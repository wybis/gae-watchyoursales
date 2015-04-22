package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

jsonCategory.respondWithJson(response, responseDto)

//println 'reset started...'
//
//try {
//	SessionUserDto sessionUser = new SessionUserDto()
//	sessionUser.id = 'system'
//	sessionUser.roleId = Role.SYS_ADMIN
//
//	def usrs = []
//
//	User usr = new User()
//	usr.id = 'vteial'
//	usr.password = '1234'
//	usr.emailId = 'vteial@gmail.com'
//	usr.firstName = 'Eialarasu'
//	usr.lastName = 'VT'
//	usr.status = UserStatus.ACTIVE
//	usr.roleId = Role.SYS_ADMIN
//	usrs << usr
//
//	usr = new User()
//	usr.id = 'donkhan'
//	usr.password = '1234'
//	usr.emailId = 'routetokamil@gmail.com'
//	usr.firstName = 'Kamil'
//	usr.lastName = 'Khan'
//	usr.status = UserStatus.ACTIVE
//	usr.roleId = Role.SYS_ADMIN
//	usrs << usr
//
//	usr = new User()
//	usr.id = 'jayaramanhari'
//	usr.password = '1234'
//	usr.emailId = 'jayaramanhari@gmail.com'
//	usr.firstName = 'Hariharasubramanian'
//	usr.lastName = 'Jayaraman'
//	usr.status = UserStatus.ACTIVE
//	usr.roleId = Role.SYS_ADMIN
//	usrs << usr
//
//	usr = new User()
//	usr.id = 'vrsumitha'
//	usr.password = '1234'
//	usr.emailId = 'sumitha.v.r@gmail.com'
//	usr.firstName = 'Sumitha'
//	usr.lastName = 'Vasanthan'
//	usr.status = UserStatus.ACTIVE
//	usr.roleId = Role.APP_ADMIN
//	usrs << usr
//
//	usrs.each { u ->
//		usrService.add(sessionUser, u)
//	}
//}
//catch(Throwable t) {
//	t.printStackTrace(out)
//}

println 'reset finished...'