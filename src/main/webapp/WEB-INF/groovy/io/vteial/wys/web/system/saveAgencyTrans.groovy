package io.vteial.wys.web.system;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Role

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
