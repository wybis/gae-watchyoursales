package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Tran
import io.vteial.wys.service.exceptions.OrderException

interface OrderService {

	void add(SessionUserDto sessionUser, OrderReceipt orderReceipt) throws OrderException

	void onTransaction(SessionUserDto sessionUser, Tran tran)
}
