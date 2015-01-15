package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto

interface AutoNumberService {

	long getNextNumber(SessionUserDto sessionUser, String key)
}
