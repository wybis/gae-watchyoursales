package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

try {
	long branchId = params.branchId as Long
	responseDto.message = "Shop '${branchId}' deleted...";
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