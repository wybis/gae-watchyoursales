import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Stock
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.OrderCategory
import io.vteial.wys.model.constants.OrderType
import io.vteial.wys.model.constants.TransactionCategory
import io.vteial.wys.model.constants.TransactionType


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	UserDto user = new UserDto()
	user.id = 'munmin2000@maxmoney'

	SessionUserDto sessionUser = sessionService.login(session, user)
	println "logged in as $sessionUser.id"

	println '-----------------------------------------------------------------'
	println 'placing dealer order started...'
	List<Stock> stocks = employeeService.getMyProductStocks(sessionUser)

	List<User> dealers = employeeService.getMyDealers(sessionUser)

	OrderReceipt orderReceipt = new OrderReceipt()
	orderReceipt.category = OrderCategory.DEALER
	orderReceipt.customerId = dealers[1].id
	orderReceipt.orders = []

	stocks.each { Stock stock ->
		println stock

		Order order = new Order()
		order.category = orderReceipt.category
		order.stockId = stock.id
		order.type = OrderType.BUY
		order.unit = 12
		order.rate = stock.product.buyRate

		orderReceipt.orders << order
		println order
	}

	orderService.add(sessionUser, orderReceipt)

	println 'placing dealer order finished...'
	println '-----------------------------------------------------------------'
	println 'processing dealer order started...'

	TranReceipt tranReceipt = new TranReceipt()
	tranReceipt.category = TransactionCategory.DEALER
	tranReceipt.customerId = dealers[1].id
	tranReceipt.trans = []

	def amount = 0
	orderReceipt.orders.each { order ->
		println order

		Tran tran = new Tran()
		tran.category = tranReceipt.category
		tran.orderId = order.id
		tran.stockId = order.stockId
		tran.type = TransactionType.BUY
		tran.unit = order.unit - 4
		tran.rate = order.rate

		tranReceipt.trans << tran
		println tran

		amount += tran.unit * tran.rate
	}

	Stock cashStock = employeeService.getMyCashStock(sessionUser)
	println cashStock

	tran = new Tran()
	tran.category = tranReceipt.category
	tran.stockId = cashStock.id
	tran.type = TransactionType.SELL
	tran.unit = amount
	tran.rate = 1
	//tran.rate = stock.product.sellRate

	tranReceipt.trans << tran
	println tran

	tranService.add(sessionUser, tranReceipt)

	tranReceipt.trans.each { tran -> println tran }

	println 'processing dealer order finished...'
	println '-----------------------------------------------------------------'
	println 'placing customer order started...'

	List<User> customers = employeeService.getMyCustomers(sessionUser)

	orderReceipt = new OrderReceipt()
	orderReceipt.category = OrderCategory.CUSTOMER
	orderReceipt.customerId = customers[1].id
	orderReceipt.orders = []

	stocks.each { Stock stock ->
		println stock

		Order order = new Order()
		order.category = orderReceipt.category
		order.stockId = stock.id
		order.type = OrderType.SELL
		order.unit = 7
		order.rate = stock.product.sellRate

		orderReceipt.orders << order

		println order
	}

	orderService.add(sessionUser, orderReceipt)

	println 'placing customer order finished...'
	println '-----------------------------------------------------------------'
	println 'processing customer order started...'

	tranReceipt = new TranReceipt()
	tranReceipt.category = TransactionCategory.CUSTOMER
	tranReceipt.customerId = customers[1].id
	tranReceipt.trans = []

	amount = 0
	orderReceipt.orders.each { order ->
		println order

		Tran tran = new Tran()
		tran.category = tranReceipt.category
		tran.orderId = order.id
		tran.stockId = order.stockId
		tran.type = TransactionType.SELL
		tran.unit = order.unit - 1
		tran.rate = order.rate

		tranReceipt.trans << tran
		println tran

		amount += tran.unit * tran.rate
	}

	//	Stock stock = employeeService.getMyCashStock(sessionUser)
	//	println stock

	tran = new Tran()
	tran.category = tranReceipt.category
	tran.stockId = cashStock.id
	tran.type = TransactionType.BUY
	tran.unit = amount
	tran.rate = 1
	//tran.rate = stock.product.sellRate

	tranReceipt.trans << tran
	println tran

	tranService.add(sessionUser, tranReceipt)

	tranReceipt.trans.each { tran -> println tran }

	println 'processing customer order finished...'
	println '-----------------------------------------------------------------'

	//sessionService.logout()
	println "logged out as $sessionUser.id"
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
