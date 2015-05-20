package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

try {

	def deleteEntitys = { entityName, ibranchId ->
		def keys = datastore.execute {
			select keys from entityName
			where branchId == ibranchId
		}
		keys.each {	key ->
			key.delete()
		}
	}

	long ibranchId = params.branchId as Long

	def entityNames = []
	entityNames << Tran.class.simpleName
	entityNames << TranReceipt.class.simpleName
	entityNames << Account.class.simpleName
	entityNames << Product.class.simpleName
	entityNames << User.class.simpleName

	entityNames.each { entityName ->
		console.println("$entityName deletion started...")
		deleteEntitys(entityName, ibranchId)
		console.println("$entityName deletion finished...")
	}
	Branch branch = Branch.get(ibranchId)
	branch.delete()

	responseDto.message = "Shop '${ibranchId}' deleted...";
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)