package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Account
import io.vteial.seetu.model.AccountTransaction
import io.vteial.seetu.service.exceptions.InSufficientFundException
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface AccountService {

	void add(SessionUserDto sessionUser, Account account) throws ModelAlreadyExistException

	void addTransaction(SessionUserDto sessionUser, AccountTransaction accountTran) throws InSufficientFundException
}
