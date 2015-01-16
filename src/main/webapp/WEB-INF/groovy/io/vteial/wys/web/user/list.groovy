package io.vteial.wys.web.user

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.User


ResponseDto responseDto = new ResponseDto()

def users = User.findAll()
responseDto.data = users

jsonCategory.respondWithJson(response, responseDto)