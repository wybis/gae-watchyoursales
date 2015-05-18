package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Role
import io.vteial.wys.model.Tran
import io.vteial.wys.model.constants.TransactionCategory
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

try {
	List<Tran> models = []

	def entitys = null
	if(sessionDto.roleId == Role.ID_MANAGER) {
		entitys = datastore.execute {
			from Tran.class.simpleName
			where category == TransactionCategory.LEDGER
			and branchId == sessionDto.branchId
			sort desc by date
		}
	}
	else {
		entitys = datastore.execute {
			from Tran.class.simpleName
			where category == TransactionCategory.LEDGER
			and byUserId == sessionDto.id
			sort desc by date
		}
	}

	entitys.each { entity ->
		Tran model = entity as Tran
		model.computeAmount()
		models <<  model
	}

	responseDto.data = models;
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'Fetching ledger transactions failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)