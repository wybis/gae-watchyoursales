package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Transfer
import io.vteial.wys.service.exceptions.TransactionException

interface TransferService {

	void add(SessionDto sessionUser, Transfer transfer) throws TransactionException
}
