package io.vteial.wys.web.system;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Product
import io.vteial.wys.model.Role
import io.vteial.wys.model.Stock
import io.vteial.wys.model.constants.ProductType

println 'saving agency master started...'

try {

	SessionUserDto sessionUser = new SessionUserDto()
	sessionUser.id = 'vteial'
	sessionUser.roleId = Role.SYS_ADMIN

	Agency agency = jsonCategory.parseJson(request, Agency.class)

	agencyService.add(sessionUser, agency)

	sessionUser = new SessionUserDto()
	sessionUser.id = agency.employees[0].id
	sessionUser.roleId = agency.employees[0].roleId

	agency.employees.each { t ->
		t.agencyId = agency.id
		employeeService.add(sessionUser, t)
	}

	agency.dealers.each { t ->
		t.agencyId = agency.id
		dealerService.add(sessionUser, t)
	}

	agency.customers.each { t ->
		t.agencyId = agency.id
		customerService.add(sessionUser, t)
	}

	Product model = new Product()
	model.with {
		type = ProductType.CASH
		code = 'CIH'
		name = 'Cash'
		baseUnit = 1
		denominator = 1
		buyRate = 1
		buyPercent = 1
		sellRate = 1
		sellPercent = 1
		agencyId = agency.id
	}
	agency.products << model
	model = new Product()
	model.with {
		type = ProductType.PROFIT
		code = 'PFT'
		name = 'Profit'
		baseUnit = 1
		denominator = 1
		buyRate = 1
		buyPercent = 1
		sellRate = 1
		sellPercent = 1
		agencyId = agency.id
	}
	agency.products << model
	agency.products.each { t ->
		t.agencyId = agency.id
		productService.add(sessionUser, t)
	}

	agency.employees.each { e ->
		UserDto user = new UserDto()
		user.id = e.id
		sessionUser = sessionService.login(session, user)
		Stock stock = employeeService.getMyCashStock(sessionUser)
		stock.product = Product.get(stock.productId)
		stock.depositHandStock(50000)
		stock.save()
		stock.product.save();
	}
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'saving agency master finished...'
