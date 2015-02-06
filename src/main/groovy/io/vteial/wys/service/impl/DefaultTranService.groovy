package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
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
	public void add(SessionUserDto sessionUser, TranReceipt receipt) throws TransactionException {

		Date now = new Date()
		receipt.id = autoNumberService.getNextNumber(sessionUser, TranReceipt.ID_KEY)
		receipt.date = now
		receipt.status = TransactionStatus.COMPLETE
		receipt.employeeId = sessionUser.id
		receipt.agencyId = sessionUser.agencyId

		List<Tran> trans = receipt.trans
		for(int i = 0; i < trans.size(); i++) {
			Tran tran = trans.get(i)
			tran.receiptId = receipt.id
			tran.date = receipt.date

			this.addTransaction(sessionUser, tran)
		}

		receipt.prePersist(sessionUser.id)
		receipt.save()
	}

	private void addTransaction(SessionUserDto sessionUser, Tran tran) throws TransactionException {

		if(tran.orderId > 0) {
			Order order = Order.get(tran.orderId)
			if(order.status == OrderStatus.COMPLETE) {
				String s = "Order $tran.orderId has already sold out"
				throw new TransactionException(s);
			}
			tran.order = order
		}

		Stock stock = Stock.get(tran.stockId)
		tran.stock = stock

		Product product = Product.get(stock.productId)
		stock.product = product

		tran.baseUnit = product.baseUnit
		//tran.computeAmount()
		tran.order.baseUnit = product.baseUnit
		//tran.order.computeAmount()

		if(tran.type == TransactionType.BUY) {
			product.computeHandStockAverage(tran.unit, tran.rate)

			stock.depositHandStock(tran.unit)
		} else {

			stock.withdrawHandStock(tran.unit)
		}
		tran.averageRate = product.handStockAverage
		tran.status = TransactionStatus.COMPLETE

		tran.employeeId = sessionUser.id
		tran.agencyId = sessionUser.agencyId

		tran.id = autoNumberService.getNextNumber(sessionUser, Tran.ID_KEY)

		tran.prePersist(sessionUser.id)
		tran.save()

		if(tran.orderId > 0) {

			orderService.onTransaction(sessionUser, tran);
		}

		stock.computeAvailableStock();
		stock.preUpdate(sessionUser.id)
		stock.save()

		product.computeAvailableStockAverage(tran.rate)
		product.preUpdate(sessionUser.id)
		product.save()
	}
}
