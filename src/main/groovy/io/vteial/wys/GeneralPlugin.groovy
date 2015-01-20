package io.vteial.wys

import groovyx.gaelyk.plugins.PluginBaseScript
import io.vteial.wys.service.impl.DefaultAccountService
import io.vteial.wys.service.impl.DefaultAgencyService
import io.vteial.wys.service.impl.DefaultAutoNumberService
import io.vteial.wys.service.impl.DefaultCustomerService
import io.vteial.wys.service.impl.DefaultDealerService
import io.vteial.wys.service.impl.DefaultEmployeeService
import io.vteial.wys.service.impl.DefaultItemService
import io.vteial.wys.service.impl.DefaultSessionService
import io.vteial.wys.service.impl.DefaultStockService
import io.vteial.wys.service.impl.DefaultUserService

class GeneralPlugin extends PluginBaseScript {

	@Override
	public Object run() {
		log.info "Registering GeneralPlugin started..."

		DefaultAutoNumberService anS = new DefaultAutoNumberService()

		DefaultSessionService sesS = new DefaultSessionService()
		sesS.autoNumberService = anS
		sesS.app = app
		sesS.localMode = localMode
		sesS.appUserService = users

		DefaultAccountService accS = new DefaultAccountService()
		accS.autoNumberService = anS

		DefaultStockService stkS = new DefaultStockService()
		stkS.autoNumberService = anS

		DefaultUserService usrS = new DefaultUserService()
		usrS.autoNumberService = anS
		usrS.accountService = accS

		DefaultDealerService dlrS = new DefaultDealerService()
		dlrS.autoNumberService = anS
		dlrS.accountService = accS

		DefaultCustomerService cusS = new DefaultCustomerService()
		cusS.autoNumberService = anS
		cusS.accountService = accS

		DefaultAgencyService agnS = new DefaultAgencyService()
		agnS.autoNumberService = anS
		agnS.accountService = accS
		agnS.dealerService = dlrS
		agnS.customerService = cusS

		DefaultItemService itmS = new DefaultItemService()
		itmS.autoNumberService = anS
		itmS.stockService = stkS

		DefaultEmployeeService empS = new DefaultEmployeeService()
		empS.autoNumberService = anS
		empS.accountService = accS
		empS.stockService = stkS

		binding {
			jsonCategory      = JacksonCategory
			jsonObjectMapper  = JacksonCategory.jsonObjectMapper
			sessionService    = sesS
			autoNumberService = anS
			accountService    = accS
			usrService        = usrS
			agencyService     = agnS
			itemService       = itmS
			stockService      = stkS
			employeeService   = empS
			dealerService     = dlrS
			customerService   = cusS
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
