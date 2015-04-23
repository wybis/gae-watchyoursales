package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Tran
import io.vteial.wys.service.exceptions.OrderException

interface OrderService {

	void add(SessionDto sessionUser, OrderReceipt orderReceipt) throws OrderException

	void onTransaction(SessionDto sessionUser, Tran tran)
}
