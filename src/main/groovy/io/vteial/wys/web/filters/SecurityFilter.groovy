package io.vteial.wys.web.filters;

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.service.SessionService

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

public class SecurityFilter implements Filter {

	GroovyLogger log = new GroovyLogger(SecurityFilter.class.getName())

	Map<String, Boolean> allowedPaths = [:]

	public void init(FilterConfig filterConfig) {
		allowedPaths['/sessions/properties'] = true
		allowedPaths['/sessions/login'] = true
		allowedPaths['/sessions/logout'] = true
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req

		if(!request.requestURI.startsWith('/sessions/')) {
			chain.doFilter(req, res)
			return
		}

		if(allowedPaths[request.requestURI]) {
			chain.doFilter(req, res)
			return
		}

		HttpServletResponse response = (HttpServletResponse) res

		HttpSession session = request.session;
		SessionDto sessionUser  = (SessionDto) session.getAttribute(SessionService.SESSION_USER_KEY);
		if (sessionUser == null) {
			response.sendError(419);
			return;
		}
		String arrivedUserId = request.getHeader('X-UserId')
		String s = "${request.requestURI} => ${sessionUser.userId} : ${arrivedUserId}"
		//System.out.println(s)
		if(arrivedUserId != 'null' && sessionUser.userId != arrivedUserId) {
			response.sendError(419);
			return;
		}

		chain.doFilter(req, res)
	}
}
