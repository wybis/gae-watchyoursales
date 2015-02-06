package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.service.exceptions.TransactionException

interface TranService {

	void add(SessionUserDto sessionUser, TranReceipt tranReceipt) throws TransactionException
}
