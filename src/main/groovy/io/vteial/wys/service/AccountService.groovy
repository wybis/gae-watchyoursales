package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.AccountTransaction
import io.vteial.wys.service.exceptions.InSufficientFundException
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface AccountService {

	void add(SessionUserDto sessionUser, Account account) throws ModelAlreadyExistException

	void addTransaction(SessionUserDto sessionUser, AccountTransaction accountTran) throws InSufficientFundException
}
