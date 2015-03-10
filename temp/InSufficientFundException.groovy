package io.vteial.wys.service.exceptions

import groovy.transform.Canonical
import io.vteial.wys.model.Account
import io.vteial.wys.model.TranAccount

@Canonical
class InSufficientFundException extends Exception {

	Account account

	TranAccount accountTran
}
