package io.vteial.wys.web.account

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account

ResponseDto responseDto = new ResponseDto()

def accounts = Account.findAll()
responseDto.data = accounts

jsonCategory.respondWithJson(response, responseDto)
