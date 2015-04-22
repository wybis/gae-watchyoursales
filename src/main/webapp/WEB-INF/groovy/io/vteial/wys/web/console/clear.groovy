package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Address
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Country
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Product
import io.vteial.wys.model.Role
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

StringWriter sw = new StringWriter()
PrintWriter pw = new PrintWriter(sw);
pw.println 'clear started...'

try {

	def entities = AutoNumber.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' autonNumbers deleted'

	entities = Country.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' countrys deleted'

	entities = Address.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' addresses deleted'

	entities = Role.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' roles deleted'

	entities = User.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' users deleted'

	entities = Product.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' products deleted'

	entities = Account.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' stocks deleted'

	entities = Branch.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' branchs deleted'


	entities = OrderReceipt.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' orderReceipts deleted'

	entities = Order.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' orders deleted'

	entities = TranReceipt.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' tranReceipts deleted'

	entities = Tran.findAll()
	entities.each { entity ->
		entity.delete()
	}
	pw.println entities.size() + ' trans deleted'
}
catch(Throwable t) {
	F	t.printStackTrace(pw)
	responseDto.type = ResponseDto.ERROR
}

pw.println 'clear finished...'

responseDto.data = sw.toString()

jsonCategory.respondWithJson(response, responseDto)
