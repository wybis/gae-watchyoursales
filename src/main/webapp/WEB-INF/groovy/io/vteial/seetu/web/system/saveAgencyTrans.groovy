package io.vteial.seetu.web.system;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Role

println 'init started...'

try {
	SessionUserDto sessionUser = new SessionUserDto()
	sessionUser.id = 'vteial'
	sessionUser.roleId = Role.SYS_ADMIN
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'init finished...'
