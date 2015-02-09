email						to : '/receiveEmail.groovy'

jabber	chat,	 			to : '/receiveJabberMessage.groovy'
jabber 	presence,			to : '/receiveJabberpresence.groovy'
jabber	subscription, 		to : '/receiveJabberSubscription.groovy'

get 	'/favicon.ico',		redirect : '/assets/favicon.png'
get     '/',				redirect : '/index'
get     '/index',			forward  : '/index.groovy'
get 	'/info',			forward  : '/info.groovy'
get		'/ping',			forward  : '/ping.groovy'
all 	'/_ah/warmup',		forward  : '/ping.groovy'

// cron
get 	'/cron/dailyBackup',			forward : '/cron/dailyBackup.groovy'

// data
get 	'/system/reset',    			forward : '/io/vteial/wys/web/system/reset.groovy'
get 	'/system/init',	    			forward : '/io/vteial/wys/web/system/init.groovy'
post 	'/system/saveAgencyMaster',		forward : '/io/vteial/wys/web/system/saveAgencyMaster.groovy'
post 	'/system/saveAgencyTrans',		forward : '/io/vteial/wys/web/system/saveAgencyTrans.groovy'
get 	'/system/clear',   				forward : '/io/vteial/wys/web/system/clear.groovy'
get 	'/system/initTransactions',   	forward : '/io/vteial/wys/web/system/initTransactions.groovy'
get 	'/system/clearTransactions',   	forward : '/io/vteial/wys/web/system/clearTransactions.groovy'

// session
get  	'/sessions/properties',			forward : '/io/vteial/wys/web/session/properties.groovy'
post 	'/sessions/login',     			forward : '/io/vteial/wys/web/session/login.groovy'
get  	'/sessions/logout',    			forward : '/io/vteial/wys/web/session/logout.groovy'

get		'/products',    				forward : '/io/vteial/wys/web/employee/products.groovy'
get		'/stocks',    					forward : '/io/vteial/wys/web/employee/stocks.groovy'
get		'/customers',    				forward : '/io/vteial/wys/web/employee/customers.groovy'
get		'/dealers',    					forward : '/io/vteial/wys/web/employee/dealers.groovy'
get		'/employees',    				forward : '/io/vteial/wys/web/employee/employees.groovy'
get		'/accounts',    				forward : '/io/vteial/wys/web/employee/accounts.groovy'

// user
get      '/users',          			forward : '/io/vteial/wys/web/user/list.groovy'

// agency
get      '/agencys',         			forward : '/io/vteial/wys/web/agency/list.groovy'

get      '/agencys/@id/accounts',       forward : '/io/vteial/wys/web/account/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/products',      	forward : '/io/vteial/wys/web/product/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/stocks',      	forward : '/io/vteial/wys/web/stock/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/employees',      				forward : '/io/vteial/wys/web/employee/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/employees/@employeeId/stocks', 	forward : '/io/vteial/wys/web/employee/findByAgencyIdAndEmployeeId.groovy?agencyId=@id&employeeId=@employeeId'

get      '/agencys/@id/dealers',       	forward : '/io/vteial/wys/web/dealer/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/customers',      forward : '/io/vteial/wys/web/customer/findByAgencyId.groovy?agencyId=@id'

// customer
get      '/customers/all',     			forward : '/io/vteial/wys/web/customer/all.groovy'
get      '/customers',         			forward : '/io/vteial/wys/web/customer/list.groovy'

// account
get      '/accounts/all',         		forward : '/io/vteial/wys/web/account/all.groovy'
get      '/accounts',         			forward : '/io/vteial/wys/web/account/list.groovy'

