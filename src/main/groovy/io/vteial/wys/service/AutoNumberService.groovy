package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto

interface AutoNumberService {

	long getNextNumber(SessionDto sessionUser, String key)
}
