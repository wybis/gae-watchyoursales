package io.vteial.wys.web.customer

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Customer

ResponseDto responseDto = new ResponseDto()

def customers = Customer.findAll()
responseDto.data = customers

jsonCategory.respondWithJson(response, responseDto)
