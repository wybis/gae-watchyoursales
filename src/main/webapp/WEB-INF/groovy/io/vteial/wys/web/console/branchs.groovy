package io.vteial.wys.web.console

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType

ResponseDto responseDto = new ResponseDto()

def branchs = Branch.findAll()
branchs.each { Branch branch ->
	def entitys = datastore.execute {
		from Account.class.getSimpleName()
		where branchId == branch.id
	}
	def models = []
	entitys.each { entity ->
		Account model = entity as Account
		models << model
	}
	branch.accounts = models

	entitys = datastore.execute {
		from Product.class.getSimpleName()
		where branchId == branch.id
	}
	models = []
	entitys.each { entity ->
		Product model = entity as Product
		models << model
	}
	branch.products = models

	entitys = datastore.execute {
		from User.class.getSimpleName()
		where branchId == branch.id
		and type == UserType.EMPLOYEE
	}
	models = []
	entitys.each { entity ->
		User model = entity as User
		models << model
	}
	branch.employees = models

	entitys = datastore.execute {
		from User.class.getSimpleName()
		where branchId == branch.id
		and type == UserType.DEALER
	}
	models = []
	entitys.each { entity ->
		User model = entity as User
		models << model
	}
	branch.dealers = models

	entitys = datastore.execute {
		from User.class.getSimpleName()
		where branchId == branch.id
		and type == UserType.CUSTOMER
	}
	models = []
	entitys.each { entity ->
		User model = entity as User
		models << model
	}
	branch.customers = models
}
responseDto.data = branchs

jsonCategory.respondWithJson(response, responseDto)
