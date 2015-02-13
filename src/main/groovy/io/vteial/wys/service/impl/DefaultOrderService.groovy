package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.model.Tran
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.model.constants.OrderType
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.OrderService
import io.vteial.wys.service.exceptions.OrderException

@GaelykBindings
class DefaultOrderService extends AbstractService implements OrderService {

	GroovyLogger log = new GroovyLogger(DefaultOrderService.class.getName())

	@Override
	public void add(SessionUserDto sessionUser, OrderReceipt receipt) throws OrderException {

		Date now = new Date()
		receipt.id = autoNumberService.getNextNumber(sessionUser, OrderReceipt.ID_KEY)
		receipt.date = now
		receipt.status = OrderStatus.PENDING
		receipt.employeeId = sessionUser.id
		receipt.agencyId = sessionUser.agencyId

		List<Order> orders = receipt.orders
		for(int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i)
			order.receiptId = receipt.id
			order.date = receipt.date

			this.addOrder(sessionUser, order)
		}

		receipt.prePersist(sessionUser.id)
		receipt.save()
	}

	private void addOrder(SessionUserDto sessionUser, Order order) throws OrderException {

		Stock stock = Stock.get(order.stockId)
		order.stock = stock

		Product product = Product.get(stock.productId)
		stock.product = product
		
		order.productCode = product.code
		order.baseUnit = product.baseUnit
		//order.computeAmount()
		
		if(order.type == OrderType.BUY) {
			product.computeVirtualStockAverage(order.unit, order.rate)

			stock.depositVirtualStockBuy(order.unit)
		} else {
			stock.depositVirtualStockSell(order.unit)
		}
		order.averageRate = product.virtualStockAverage
		order.status = OrderStatus.PENDING

		order.employeeId = sessionUser.id
		order.agencyId = sessionUser.agencyId

		order.id = autoNumberService.getNextNumber(sessionUser, Order.ID_KEY)

		order.prePersist(sessionUser.id)
		order.save()

		stock.computeAvailableStock();
		stock.preUpdate(sessionUser.id)
		stock.save()

		product.computeAvailableStockAverage(order.rate)
		product.preUpdate(sessionUser.id)
		product.save()
	}

	@Override
	public void onTransaction(SessionUserDto sessionUser, Tran tran) {

		Stock stock = tran.stock
		if(tran.type == TransactionType.BUY) {
			stock.withdrawVirtualStockBuy(tran.unit)
		} else {
			stock.withdrawVirtualStockSell(tran.unit)
		}

		Order order = tran.order
		if(order.unit == tran.unit) {
			order.status = OrderStatus.COMPLETE
		} else {
			order.unit -= tran.unit
			order.computeAmount()
		}

		order.preUpdate(sessionUser.id)
		order.save()

		def keys = datastore.execute {
			select keys from Order.class.getSimpleName()
			where receiptId == order.receiptId
			and status == OrderStatus.PENDING
		}
		if(keys.size() == 0) {
			OrderReceipt receipt = OrderReceipt.get(order.receiptId)

			receipt.status = OrderStatus.COMPLETE

			receipt.preUpdate(sessionUser.id)
			receipt.save()
		}
	}
}