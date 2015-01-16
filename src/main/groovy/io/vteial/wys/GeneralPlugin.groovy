package io.vteial.wys

import groovyx.gaelyk.plugins.PluginBaseScript
import io.vteial.wys.service.impl.DefaultAccountService
import io.vteial.wys.service.impl.DefaultAgencyService
import io.vteial.wys.service.impl.DefaultAutoNumberService
import io.vteial.wys.service.impl.DefaultCustomerService
import io.vteial.wys.service.impl.DefaultDealerService
import io.vteial.wys.service.impl.DefaultEmployeeService
import io.vteial.wys.service.impl.DefaultSessionService
import io.vteial.wys.service.impl.DefaultUserService

class GeneralPlugin extends PluginBaseScript {

	@Override
	public Object run() {
		log.info "Registering GeneralPlugin started..."

		DefaultAutoNumberService anS = new DefaultAutoNumberService()

		DefaultSessionService sS = new DefaultSessionService()
		sS.autoNumberService = anS
		sS.app = app
		sS.localMode = localMode
		sS.appUserService = users

		DefaultAccountService accS = new DefaultAccountService()
		accS.autoNumberService = anS

		DefaultUserService uS = new DefaultUserService()
		uS.autoNumberService = anS
		uS.accountService = accS

		DefaultAgencyService aS = new DefaultAgencyService()
		aS.autoNumberService = anS
		aS.accountService = accS

		DefaultEmployeeService eS = new DefaultEmployeeService()
		eS.autoNumberService = anS
		eS.accountService = accS

		DefaultDealerService dS = new DefaultDealerService()
		dS.autoNumberService = anS
		dS.accountService = accS

		DefaultCustomerService cS = new DefaultCustomerService()
		cS.autoNumberService = anS
		cS.accountService = accS

		binding {
			jsonCategory      = JacksonCategory
			jsonObjectMapper  = JacksonCategory.jsonObjectMapper
			sessionService    = sS
			autoNumberService = anS
			accountService    = accS
			userService       = uS
			agencyService     = aS
			employeeService   = eS
			dealerService     = dS
			customerService   = cS
		}

		routes {
		}

		before {
			//log.info "Visiting ${request.requestURI}"
			//binding.uri = request.requestURI
		}

		after {
			//log.info "Exiting ${request.requestURI}"
			//log.info "groovlet returned $result from its execution"
		}

		log.info "Registering GeneralPlugin finished..."
	}
}
