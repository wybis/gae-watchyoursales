package io.vteial.wys.web.account

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.AccountTransaction

ResponseDto responseDto = new ResponseDto()

def accountTrans = AccountTransaction.findAll()
responseDto.data = accountTrans

jsonCategory.respondWithJson(response, responseDto)

