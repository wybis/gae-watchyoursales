import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Stock
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.constants.OrderType
import io.vteial.wys.model.constants.TransactionType


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	UserDto user = new UserDto()
	user.id = 'munmin2000@maxmoney'

	SessionUserDto sessionUser = sessionService.login(session, user)
	println sessionUser

	List<Stock> stocks = employeeService.getStocks(sessionUser)
	
	OrderReceipt orderReceipt = new OrderReceipt()
	orderReceipt.orders = []
	stocks.each { stock ->
		println stock

		Order order = new Order()
		order.stockId = stock.id
		order.type = OrderType.BUY
		order.unit = 12
		order.rate = stock.product.buyRate

		orderReceipt.orders << order

		println order
	}

	orderService.add(sessionUser, orderReceipt)

	println '-----------------------------------------------------------------'

	TranReceipt tranReceipt = new TranReceipt()
	tranReceipt.trans = []

	orderReceipt.orders.each { order ->
		println order

		Tran tran = new Tran()
		tran.orderId = order.id
		tran.stockId = order.stockId
		tran.type = TransactionType.BUY
		tran.unit = order.unit - 4
		tran.rate = order.rate

		tranReceipt.trans << tran

		println tran
	}

	tranService.add(sessionUser, tranReceipt)

	println '-----------------------------------------------------------------'

	tranReceipt.trans.each { tran -> println tran }
}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''



//	def entitys = datastore.execute {
//		from Product.class.simpleName
//		where agencyId == 1
//	}
//	entitys.each { entity ->
//		Product model = entity as Product
//		stockService.onProductCreate(sessionUser, model)
//	}

//	Employee employee = new Employee();
//	Class clazz = employee.getClass()
//	println clazz
//	println clazz.getName()
//	println clazz.getSimpleName()
//
//	clazz = Employee.class
//	println clazz
//	println clazz.name
//	println clazz.getSimpleName()
//	println clazz.simpleName

//	def entitys = datastore.execute {
//		from Employee.class.getSimpleName()
//		where agencyId == 1
//	}
//
//	entitys.each { entity ->
//		Employee employee = entity as Employee
//		println employee
//	}
