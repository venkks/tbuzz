package com.sudasuda.app.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sudasuda.app.controller.HomeController;
import com.sudasuda.app.domain.Link;
import com.sudasuda.app.domain.User;
import com.sudasuda.app.service.UserService;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "Login and encoding filter", urlPatterns = {
		"/AddLink", "/SignOut", "/Spam", "/FollowManager", "/VoteUp",
		"/FollowUserManager", "/Analytics" })
public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginFilter.class);

	private UserService userService = new UserService();

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		logger.info("encoding filter uri :" + request.getRequestURI()
				+ ", url:" + request.getRequestURL());

		System.out.println("encoding filter uri :" + request.getRequestURI()
				+ ", url:" + request.getRequestURL());
		
		boolean comitted = false;

		if (request.getRequestURI().contains("SignIn")) {
			comitted = true;
			chain.doFilter(request, response);
		}

		List<Link> links = null;

		if (request.getParameter("title") != null) {
			String title = URLDecoder.decode(request.getParameter("title"),
					"UTF-8");
			logger.info("Title in login filter ------------>" + title);
		}

		User user = (User) request.getSession().getAttribute("userInfo");

		System.out.println("Login filter ********************************");

		if (user == null) {
			String uuid = CookieUtil.getCookieValue(request,
					CookieUtil.COOKIE_NAME);
			System.out.println("uuid==" + uuid);

			if (uuid != null) {
				user = userService.findUUID(uuid);

				if (user != null) {
					request.getSession().setAttribute("userInfo", user);
					CookieUtil.addCookie(response, CookieUtil.COOKIE_NAME,
							uuid, 2592000);
				} else {
					CookieUtil.removeCookie(response, CookieUtil.COOKIE_NAME);
				}
			}

		}
		if (user == null && !comitted) {
			response.sendRedirect(request.getContextPath() + "/SignIn?page=signin");
		} else {
			System.out.println("Local addr ******" + request.getRequestURI()
					+ ", remote addr:::::::::::" + request.getRequestURL());
			if (!comitted)
				chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
