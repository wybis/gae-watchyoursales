package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.service.exceptions.TransactionException

interface TranService {

	TranReceipt findByTranReceiptId(long TranReceiptId)

	void add(SessionDto sessionUser, TranReceipt tranReceipt) throws TransactionException
}
