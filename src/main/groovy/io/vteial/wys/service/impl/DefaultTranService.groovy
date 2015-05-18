package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Order
import io.vteial.wys.model.Product
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.model.constants.TransactionStatus
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.OrderService
import io.vteial.wys.service.TranService
import io.vteial.wys.service.exceptions.TransactionException

@GaelykBindings
class DefaultTranService extends AbstractService implements TranService {

	GroovyLogger log = new GroovyLogger(DefaultTranService.class.getName())

	OrderService orderService;

	@Override
	public void add(SessionDto sessionUser, TranReceipt receipt) throws TransactionException {

		Date now = new Date()
		receipt.id = autoNumberService.getNextNumber(sessionUser, TranReceipt.ID_KEY)
		receipt.date = now
		receipt.status = TransactionStatus.COMPLETE
		receipt.byUserId = sessionUser.id
		receipt.branchId = sessionUser.branchId

		List<Tran> trans = receipt.trans
		for(int i = 0; i < trans.size(); i++) {
			Tran tran = trans.get(i)
			tran.receiptId = receipt.id
			tran.category = receipt.category
			tran.date = receipt.date
			tran.forUserId = receipt.forUserId

			try {
				this.addTransaction(sessionUser, tran)
			}
			catch(TransactionException e) {
				receipt.errorMessage = tran.errorMessage
				throw e
			}
		}

		receipt.prePersist(sessionUser.id)
		receipt.save()
	}

	private void addTransaction(SessionDto sessionUser, Tran tran) throws TransactionException {

		if(tran.orderId > 0) {
			Order order = Order.get(tran.orderId)
			if(order.status == OrderStatus.COMPLETE) {
				String s = "Order $tran.orderId has already sold out"
				throw new TransactionException(s);
			}
			tran.order = order
		}

		Account account = Account.get(tran.accountId)
		tran.account = account

		if(tran.type == TransactionType.SELL && !account.hasSufficientHandStock(tran.unit)) {
			tran.errorMessage = 'Insufficient fund or stock...'
			throw new TransactionException();
		}

		Product product = Product.get(account.productId)
		account.product = product

		tran.productCode = product.code
		tran.baseUnit = product.baseUnit
		tran.computeAmount()
		if(tran.orderId > 0) {
			tran.order.baseUnit = product.baseUnit
			//tran.order.computeAmount()
		}

		if(tran.type == TransactionType.BUY) {
			product.computeHandStockAverage(tran.unit, tran.rate)

			account.depositHandStock(tran.unit)
		} else {

			if(tran.account.type == AccountType.PRODUCT) {
				Tran ptran = this.addTransactionForProfit(sessionUser, tran)
				tran.profitTranId = ptran.id
				//tran.profitTran = tran
			}

			account.withdrawHandStock(tran.unit)
		}
		tran.averageRate = product.handStockAverage
		tran.status = TransactionStatus.COMPLETE

		tran.byUserId = sessionUser.id
		tran.branchId = sessionUser.branchId

		tran.id = autoNumberService.getNextNumber(sessionUser, Tran.ID_KEY)

		tran.prePersist(sessionUser.id)
		tran.save()

		if(tran.orderId > 0) {

			orderService.onTransaction(sessionUser, tran);
		}

		account.computeAvailableStock();
		account.preUpdate(sessionUser.id)
		account.save()

		product.computeAvailableStockAverage(tran.rate)
		product.preUpdate(sessionUser.id)
		product.save()
	}

	private Tran addTransactionForProfit(SessionDto sessionUser, Tran tran) {

		Account account = Account.get(sessionUser.profitAccountId)
		Product product = Product.get(account.productId)
		account.product = product

		Tran ptran = new Tran()
		ptran.with {
			receiptId = tran.receiptId
			category = tran.category
			productCode = product.code
			accountId = account.id
			account = account
			type = TransactionType.BUY
			baseUnit = product.baseUnit
			rate = product.buyRate
			averageRate = product.handStockAverage
			date = tran.date
			status = TransactionStatus.COMPLETE
			forUserId = tran.forUserId
		}

		double avgAmount = tran.unit * (tran.account.product.handStockAverage / tran.account.product.baseUnit)
		ptran.unit = tran.amount - avgAmount
		ptran.computeAmount()
		if(ptran.unit >= 0) {
			ptran.type = TransactionType.SELL
			account.withdrawHandStock(ptran.unit)
		} else {
			ptran.type = TransactionType.BUY
			account.depositHandStock(ptran.unit)
		}

		ptran.byUserId = sessionUser.id
		ptran.branchId = sessionUser.branchId

		ptran.id = autoNumberService.getNextNumber(sessionUser, Tran.ID_KEY)

		ptran.prePersist(sessionUser.id)
		ptran.save()

		//account.computeAvailableStock();
		account.preUpdate(sessionUser.id)
		account.save()

		//product.computeAvailableStockAverage(ptran.rate)
		product.preUpdate(sessionUser.id)
		product.save()

		return ptran
	}
}
