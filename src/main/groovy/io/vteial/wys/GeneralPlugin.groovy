package io.vteial.wys

import groovyx.gaelyk.plugins.PluginBaseScript
import io.vteial.wys.service.impl.DefaultAccountService
import io.vteial.wys.service.impl.DefaultAutoNumberService
import io.vteial.wys.service.impl.DefaultBranchService
import io.vteial.wys.service.impl.DefaultCustomerService
import io.vteial.wys.service.impl.DefaultDealerService
import io.vteial.wys.service.impl.DefaultEmployeeService
import io.vteial.wys.service.impl.DefaultOrderService
import io.vteial.wys.service.impl.DefaultProductService
import io.vteial.wys.service.impl.DefaultSessionService
import io.vteial.wys.service.impl.DefaultTranService
import io.vteial.wys.service.impl.DefaultUserService

class GeneralPlugin extends PluginBaseScript {

	@Override
	public Object run() {
		log.info "Registering GeneralPlugin started..."

		DefaultAutoNumberService anS = new DefaultAutoNumberService()

		DefaultUserService usrS = new DefaultUserService()
		usrS.autoNumberService = anS

		DefaultAccountService actS = new DefaultAccountService()
		actS.autoNumberService = anS

		DefaultSessionService sesS = new DefaultSessionService()
		sesS.autoNumberService = anS
		sesS.accountService = actS
		sesS.userService = usrS
		sesS.appUserService = users

		DefaultProductService prdS = new DefaultProductService()
		prdS.autoNumberService = anS
		prdS.accountService = actS

		DefaultEmployeeService empS = new DefaultEmployeeService()
		empS.autoNumberService = anS
		empS.accountService = actS

		DefaultDealerService dlrS = new DefaultDealerService()
		dlrS.autoNumberService = anS
		dlrS.accountService = actS

		DefaultCustomerService cusS = new DefaultCustomerService()
		cusS.autoNumberService = anS
		cusS.accountService = actS

		DefaultBranchService bchS = new DefaultBranchService()
		bchS.autoNumberService = anS
		bchS.productService = prdS
		bchS.employeeService = empS
		bchS.dealerService = dlrS
		bchS.customerService = cusS

		DefaultOrderService ordS = new DefaultOrderService()
		ordS.autoNumberService = anS

		DefaultTranService trnS = new DefaultTranService()
		trnS.autoNumberService = anS
		trnS.orderService      = ordS

		binding {
			console           = System.out
			jsonCategory      = JacksonCategory
			jsonObjectMapper  = JacksonCategory.jsonObjectMapper
			sessionService    = sesS
			autoNumberService = anS
			branchService     = bchS
			accountService    = actS
			productService    = prdS
			employeeService   = empS
			dealerService     = dlrS
			customerService   = cusS
			orderService      = ordS
			tranService       = trnS
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
