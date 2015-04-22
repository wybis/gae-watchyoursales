package io.vteial.wys.service;

import io.vteial.wys.model.User

interface AutoNumberService {

	long getNextNumber(User sessionUser, String key)
}
