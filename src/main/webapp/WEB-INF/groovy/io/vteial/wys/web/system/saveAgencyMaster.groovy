package io.vteial.wys.web.system;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.Role
import io.vteial.wys.model.Account

println 'saving agency master started...'

try {

	SessionUserDto sessionUser = new SessionUserDto()
	sessionUser.userId = 'sadmin'
	sessionUser.roleId = Role.SYS_ADMIN

	Branch agency = jsonCategory.parseJson(request, Branch.class)

	agencyService.add(sessionUser, agency)

	sessionUser = new SessionUserDto()
	sessionUser.id = agency.employees[0].id
	sessionUser.roleId = agency.employees[0].roleId

	agency.products.each { t ->
		t.agencyId = agency.id
		productService.add(sessionUser, t)
	}

	agency.employees.each { t ->
		t.agencyId = agency.id
		employeeService.add(sessionUser, t)
	}

	agency.dealers.each { t ->
		t.agencyId = agency.id
		t.roleId = Role.AGNECY_DEALER
		dealerService.add(sessionUser, t)
	}

	agency.customers.each { t ->
		t.agencyId = agency.id
		t.roleId = Role.AGNECY_CUSTOMER
		customerService.add(sessionUser, t)
	}

	agency.employees.each { e ->
		Account stock = Account.get(e.cashStockId)
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
