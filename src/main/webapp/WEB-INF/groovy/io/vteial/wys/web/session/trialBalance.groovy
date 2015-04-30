package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Role
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

try {
	TB tb = new TB()
	tb.generatedBy = sessionDto.userId
	tb.generatedTime = new Date()

	def entitys = null
	//	if(sessionDto.roleId == Role.ID_MANAGER) {
	entitys = datastore.execute {
		from Account.class.simpleName
		where branchId == sessionDto.branchId
	}
	//	}
	//	else {
	//		entitys = datastore.execute {
	//			from Account.class.simpleName
	//			where userId == sessionDto.id
	//		}
	//	}

	entitys.each { entity ->
		Account account = entity as Account
		TBI tbi = new TBI()
		tbi.with {
			id = account.id
			name = account.name
			aliasName = account.aliasName
			type = account.type
			amount = account.amount
			userId = account.userId
		}
		if(account.amount > 0) {
			tbi.debit = account.amount
			tb.debitTotal += tbi.debit
		}
		else {
			tbi.credit = account.amount
			tb.creditTotal += tbi.credit
		}
		tb.items << tbi
	}

	responseDto.data = tb
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'Fetching trial balance failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)


class TBI {
	long id
	String name
	String aliasName
	String type
	double amount
	double credit
	double debit
	long userId
}

class TB {
	String generatedBy
	Date generatedTime
	double creditTotal
	double debitTotal
	List<TBI> items = []
}