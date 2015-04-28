import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.FORBIDDEN
responseDto.message = 'Forbidden! You have login to access this resource...'

jsonCategory.respondWithJson(response, responseDto)
