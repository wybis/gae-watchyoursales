package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.TranAccount
import io.vteial.wys.model.constants.AccountTransactionStatus
import io.vteial.wys.model.constants.AccountTransactionType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.exceptions.InSufficientFundException
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultAccountService extends AbstractService implements AccountService {

	GroovyLogger log = new GroovyLogger(DefaultAccountService.class.getName())

	@Override
	public void add(SessionUserDto sessionUser, Account account)
	throws ModelAlreadyExistException {
		//log.info "Pre-" + account.toString()

		account.id = autoNumberService.getNextNumber(sessionUser, Account.ID_KEY)

		account.prePersist(sessionUser.id)
		account.save()

		//log.info "Post-" + account.toString()
	}

	@Override
	public void addTransaction(SessionUserDto sessionUser, TranAccount accountTran) throws InSufficientFundException {
		//log.info "Pre-" + accountTran.toString()

		Account account = Account.get(accountTran.accountId)

		if (accountTran.type == AccountTransactionType.WITHDRAW && !account.hasSufficientBalance(accountTran.amount)) {
			log.info "InSufficientFundException-" + account.toString()
			throw new InSufficientFundException(account : account,  accountTran : accountTran)
		}

		if(accountTran.type == AccountTransactionType.WITHDRAW) {
			account.withdraw(accountTran.amount)
		} else {
			account.deposit(accountTran.amount)
		}

		account.preUpdate(sessionUser.id)
		account.save()

		accountTran.balance = account.balance

		if(!accountTran.description) {
			accountTran.description = '-'
		}

		if(!accountTran.date) {
			accountTran.date = new Date()
		}

		accountTran.status = AccountTransactionStatus.COMPLETE

		accountTran.employeeId = sessionUser.id

		accountTran.id = autoNumberService.getNextNumber(sessionUser, TranAccount.ID_KEY)

		accountTran.prePersist(sessionUser.id)
		accountTran.save()

		//log.info "Post-" + accountTran.toString()
	}
}
