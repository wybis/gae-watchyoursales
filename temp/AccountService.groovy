package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.TranAccount
import io.vteial.wys.service.exceptions.InSufficientFundException
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface AccountService {

	void add(SessionUserDto sessionUser, Account account) throws ModelAlreadyExistException

	void addTransaction(SessionUserDto sessionUser, TranAccount accountTran) throws InSufficientFundException
}
