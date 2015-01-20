email						to : '/receiveEmail.groovy'

jabber	chat,	 			to : '/receiveJabberMessage.groovy'
jabber 	presence,			to : '/receiveJabberpresence.groovy'
jabber	subscription, 		to : '/receiveJabberSubscription.groovy'

get 	'/favicon.ico',		redirect : '/assets/favicon.png'
get     '/',				redirect : '/index'
get     '/index',			forward  : '/index.groovy'
get 	'/info',			forward  : '/info.groovy'
//get	'/json',			forward  : '/json.groovy'
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

// user
get      '/users',          			forward : '/io/vteial/wys/web/user/list.groovy'
//get    '/users/user/@id', 			forward : '/io/vteial/wys/web/user/findById.groovy?id=@id'
//post   '/users/user',     			forward : '/io/vteial/wys/web/user/create.groovy'
//put    '/users/user/@id', 			forward : '/io/vteial/wys/web/user/update.groovy?id=@id'
//delete '/users/user/@id', 			forward : '/io/vteial/wys/web/user/delete.groovy?id=@id'

// agency
get      '/agencys',         			forward : '/io/vteial/wys/web/agency/list.groovy'
get      '/agencys/@id/accounts',       forward : '/io/vteial/wys/web/account/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/items',       	forward : '/io/vteial/wys/web/item/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/employees',      forward : '/io/vteial/wys/web/employee/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/dealers',       	forward : '/io/vteial/wys/web/dealer/findByAgencyId.groovy?agencyId=@id'
get      '/agencys/@id/customers',      forward : '/io/vteial/wys/web/customer/findByAgencyId.groovy?agencyId=@id'

// customer
get      '/customers/all',     			forward : '/io/vteial/wys/web/customer/all.groovy'
get      '/customers',         			forward : '/io/vteial/wys/web/customer/list.groovy'
//get    '/customers/customer/@id', 	forward : '/io/vteial/wys/web/user/findById.groovy?id=@id'
//post   '/customers/customer',     	forward : '/io/vteial/wys/web/user/create.groovy'
//put    '/customers/customer/@id', 	forward : '/io/vteial/wys/web/user/update.groovy?id=@id'
//delete '/customers/customer/@id', 	forward : '/io/vteial/wys/web/user/delete.groovy?id=@id'

// account
get      '/accounts/all',         		forward : '/io/vteial/wys/web/account/all.groovy'
get      '/accounts',         			forward : '/io/vteial/wys/web/account/list.groovy'
//get    '/accounts/account/@id', 		forward : '/io/vteial/wys/web/account/findById.groovy?id=@id'
//post   '/accounts/account',     		forward : '/io/vteial/wys/web/account/create.groovy'
//put    '/accounts/account/@id', 		forward : '/io/vteial/wys/web/account/update.groovy?id=@id'
//delete '/accounts/account/@id', 		forward : '/io/vteial/wys/web/account/delete.groovy?id=@id'

get     '/accountTransactions/all',    	forward : '/io/vteial/wys/web/account/allTransaction.groovy'

// item
get     '/items/all',          			forward : '/io/vteial/wys/web/item/all.groovy'
get     '/items',          				forward : '/io/vteial/wys/web/item/list.groovy'
//get    '/items/item/@id', 			forward : '/io/vteial/wys/web/item/findById.groovy?id=@id'
//post   '/items/item',     			forward : '/io/vteial/wys/web/item/create.groovy'
//put    '/items/item/@id', 			forward : '/io/vteial/wys/web/item/update.groovy?id=@id'
//delete '/items/item/@id', 			forward : '/io/vteial/wys/web/item/delete.groovy?id=@id'

get    '/itemTransactions/all',    		forward : '/io/vteial/wys/web/item/allTransaction.groovy'
