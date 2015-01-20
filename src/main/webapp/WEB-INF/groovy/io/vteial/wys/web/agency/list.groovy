package io.vteial.wys.web.agency

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Agency

ResponseDto responseDto = new ResponseDto()

def agencys = Agency.findAll()
responseDto.data = agencys

jsonCategory.respondWithJson(response, responseDto)
