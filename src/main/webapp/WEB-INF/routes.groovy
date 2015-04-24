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
get 	'/console/branchs',    			forward : '/io/vteial/wys/web/console/branchs.groovy'
get 	'/console/reset',    			forward : '/io/vteial/wys/web/console/reset.groovy'
get 	'/console/clear',    			forward : '/io/vteial/wys/web/console/clear.groovy'
get 	'/console/clearTransactions', 	forward : '/io/vteial/wys/web/console/clearTransactions.groovy'
post 	'/console/addBranch', 	        forward : '/io/vteial/wys/web/console/addBranch.groovy'

//get 	'/system/reset',    			forward : '/io/vteial/wys/web/system/reset.groovy'
//get 	'/system/init',	    			forward : '/io/vteial/wys/web/system/init.groovy'
//post 	'/system/saveAgencyMaster',		forward : '/io/vteial/wys/web/system/saveAgencyMaster.groovy'
//post 	'/system/saveAgencyTrans',		forward : '/io/vteial/wys/web/system/saveAgencyTrans.groovy'
//get 	'/system/clear',   				forward : '/io/vteial/wys/web/system/clear.groovy'
//get 	'/system/initTransactions',   	forward : '/io/vteial/wys/web/system/initTransactions.groovy'
//get 	'/system/clearTransactions',   	forward : '/io/vteial/wys/web/system/clearTransactions.groovy'

// session
get  	'/sessions/properties',					forward : '/io/vteial/wys/web/session/properties.groovy'
post 	'/sessions/login',     					forward : '/io/vteial/wys/web/session/login.groovy'
get  	'/sessions/logout',    					forward : '/io/vteial/wys/web/session/logout.groovy'

get		'/sessions/cash',    					forward : '/io/vteial/wys/web/session/cash.groovy'
get		'/sessions/products',    				forward : '/io/vteial/wys/web/session/products.groovy'
get		'/sessions/stocks',    					forward : '/io/vteial/wys/web/session/stocks.groovy'
get		'/sessions/customers',    				forward : '/io/vteial/wys/web/session/customers.groovy'
get		'/sessions/pendingCustomerOrders',		forward : '/io/vteial/wys/web/session/pendingCustomerOrders.groovy'
get		'/sessions/customerTransactions',		forward : '/io/vteial/wys/web/session/customerTransactions.groovy'
get		'/sessions/dealers',    				forward : '/io/vteial/wys/web/session/dealers.groovy'
get		'/sessions/pendingDealerOrders',		forward : '/io/vteial/wys/web/session/pendingDealerOrders.groovy'
get		'/sessions/dealerTransactions',			forward : '/io/vteial/wys/web/session/dealerTransactions.groovy'
get		'/sessions/employees',    				forward : '/io/vteial/wys/web/session/employees.groovy'

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

