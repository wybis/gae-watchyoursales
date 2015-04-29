package io.vteial.wys.web.console

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User

ResponseDto responseDto = new ResponseDto()

Branch branch = jsonCategory.parseJson(request, Branch.class)

log.info("adding branch started...")

SessionDto sessionUser = new SessionDto()

branchService.add(sessionUser, branch)

sessionUser.with { code = branch.code }

branch.products.each { t ->
	t.branchId = branch.id
	productService.add(sessionUser, t)
}

branch.employees.each { t ->
	t.branchId = branch.id
	t.userId = "${t.userId}@${branch.code}"
	employeeService.add(sessionUser, t)
}

branch.dealers.each { t ->
	t.branchId = branch.id
	dealerService.add(sessionUser, t)
}

branch.customers.each { t ->
	t.branchId = branch.id
	customerService.add(sessionUser, t)
}

log.info("adding branch finished...")

responseDto.data = branch

jsonCategory.respondWithJson(response, responseDto)
