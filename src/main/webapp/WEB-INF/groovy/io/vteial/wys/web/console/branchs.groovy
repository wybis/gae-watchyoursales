package io.vteial.wys.web.console

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Branch

ResponseDto responseDto = new ResponseDto()

def branchs = Branch.findAll()
responseDto.data = branchs

jsonCategory.respondWithJson(response, responseDto)
