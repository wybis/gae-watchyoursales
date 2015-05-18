email						to : '/receiveEmail.groovy'

jabber	chat,	 			to : '/receiveJabberMessage.groovy'
jabber 	presence,			to : '/receiveJabberpresence.groovy'
jabber	subscription, 		to : '/receiveJabberSubscription.groovy'

get 	'/favicon.ico',		redirect : '/assets/favicon.png'
get     '/',				redirect : '/index'
get     '/index',			forward  : '/index.groovy'
get 	'/info',			forward  : '/info.groovy'
get		'/ping',			forward  : '/ping.groovy'
get		'/forbidden',		forward  : '/forbidden.groovy'
all 	'/_ah/warmup',		forward  : '/ping.groovy'

// cron
get 	'/cron/dailyBackup',					forward : '/cron/dailyBackup.groovy'

// console
get		'/console',								forward : '/io/vteial/wys/web/console/index.groovy'
get 	'/console/clear',    					forward : '/io/vteial/wys/web/console/clear.groovy'
get 	'/console/branchs',    					forward : '/io/vteial/wys/web/console/branchs.groovy'
post 	'/console/addBranch', 	        		forward : '/io/vteial/wys/web/console/addBranch.groovy'
get 	'/console/branchs/branch/@id/reset',	forward : '/io/vteial/wys/web/console/branchReset.groovy?branchId=@id'
delete 	'/console/branchs/branch/@id', 			forward : '/io/vteial/wys/web/console/branchDelete.groovy?branchId=@id'

// session
get  	'/sessions/properties',					forward : '/io/vteial/wys/web/session/properties.groovy'
post 	'/sessions/login',     					forward : '/io/vteial/wys/web/session/login.groovy'
get  	'/sessions/logout',    					forward : '/io/vteial/wys/web/session/logout.groovy'

get		'/sessions/trialBalance',  				forward : '/io/vteial/wys/web/session/trialBalance.groovy'

get		'/sessions/ledgers',    				forward : '/io/vteial/wys/web/session/ledgers.groovy'
get		'/sessions/products',    				forward : '/io/vteial/wys/web/session/products.groovy'
get		'/sessions/stocks',    					forward : '/io/vteial/wys/web/session/stocks.groovy'

post	'/sessions/order',  					forward : '/io/vteial/wys/web/session/order.groovy'
post	'/sessions/counter',  					forward : '/io/vteial/wys/web/session/counter.groovy'

get		'/sessions/customers',    				forward : '/io/vteial/wys/web/session/customers.groovy'
get		'/sessions/pendingCustomerOrders',		forward : '/io/vteial/wys/web/session/pendingCustomerOrders.groovy'
post	'/sessions/customerTran',				forward : '/io/vteial/wys/web/session/customerTran.groovy'
get		'/sessions/customerTrans',				forward : '/io/vteial/wys/web/session/customerTrans.groovy'

get		'/sessions/dealers',    				forward : '/io/vteial/wys/web/session/dealers.groovy'
get		'/sessions/pendingDealerOrders',		forward : '/io/vteial/wys/web/session/pendingDealerOrders.groovy'
post	'/sessions/dealerTran',					forward : '/io/vteial/wys/web/session/dealerTran.groovy'
get		'/sessions/dealerTrans',				forward : '/io/vteial/wys/web/session/dealerTrans.groovy'

get		'/sessions/employees',    				forward : '/io/vteial/wys/web/session/employees.groovy'

post	'/sessions/ledger',  					forward : '/io/vteial/wys/web/session/ledger.groovy'
get		'/sessions/ledgerTrans',				forward : '/io/vteial/wys/web/session/ledgerTrans.groovy'
get		'/sessions/recentLedgerTrans',			forward : '/io/vteial/wys/web/session/recentLedgerTrans.groovy'


//-------------------------------------------------------------------------------------------------------//
get      '/agencys/@id/accounts',       forward : '/io/vteial/wys/web/account/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/products',      	forward : '/io/vteial/wys/web/product/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/stocks',      	forward : '/io/vteial/wys/web/stock/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/employees',      				forward : '/io/vteial/wys/web/employee/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/employees/@employeeId/stocks', 	forward : '/io/vteial/wys/web/employee/findByAgencyIdAndEmployeeId.groovy?agencyId=@id&employeeId=@employeeId'

get      '/agencys/@id/dealers',       	forward : '/io/vteial/wys/web/dealer/findByAgencyId.groovy?agencyId=@id'

get      '/agencys/@id/customers',      forward : '/io/vteial/wys/web/customer/findByAgencyId.groovy?agencyId=@id'


