package io.vteial.wys.web.system;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Role

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

	agency.products.each { t ->
		t.agencyId = agency.id
		productService.add(sessionUser, t)
	}
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'saving agency master finished...'
