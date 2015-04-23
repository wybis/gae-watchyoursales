package io.vteial.wys.web.system;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Role

println 'init started...'

try {
	SessionDto sessionUser = new SessionDto()
	sessionUser.id = 'vteial'
	sessionUser.roleId = Role.SYS_ADMIN
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'init finished...'
