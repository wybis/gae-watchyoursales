import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Role
import io.vteial.wys.service.SessionService
import eu.bitwalker.useragentutils.DeviceType
import eu.bitwalker.useragentutils.UserAgent

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

UserAgent userAgent = UserAgent.parseUserAgentString(headers['User-Agent'])
if(userAgent.operatingSystem.deviceType != DeviceType.COMPUTER) {
	forward '/index-m.html'
	return
}

redirect '/index-d.html'

