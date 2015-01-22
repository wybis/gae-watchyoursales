import eu.bitwalker.useragentutils.DeviceType
import eu.bitwalker.useragentutils.UserAgent

UserAgent userAgent = UserAgent.parseUserAgentString(headers['User-Agent'])
userAgent.operatingSystem.deviceType

if(localMode && userAgent.operatingSystem.deviceType == DeviceType.COMPUTER) {
	forward '/index-g.html'
	return
}

if(userAgent.operatingSystem.deviceType != DeviceType.COMPUTER) {
	forward '/index-d.html'
}
else {
	forward '/index-m.html'
}
