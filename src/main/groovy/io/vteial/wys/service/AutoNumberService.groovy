package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto

interface AutoNumberService {

	long getNextNumber(SessionUserDto sessionUser, String key)
}
