package io.vteial.wys.web.item

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Item

ResponseDto responseDto = new ResponseDto()

def items = Item.findAll()
responseDto.data = items

jsonCategory.respondWithJson(response, responseDto)
