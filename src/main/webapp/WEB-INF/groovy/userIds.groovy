import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.User

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

try {
	def models = []

	def entitys = datastore.execute {
		select userId : String from User.class.simpleName
	}

	entitys.each { entity ->
		models << ['userId' : entity.userId]
	}

	responseDto.data = models;
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'Fetching customer transactions failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)