package io.vteial.wys.web.account

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.TranAccount

ResponseDto responseDto = new ResponseDto()

def accountTrans = TranAccount.findAll()
responseDto.data = accountTrans

jsonCategory.respondWithJson(response, responseDto)

