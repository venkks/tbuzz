package com.sudasuda.app.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.RedirectView;


import com.sudasuda.app.domain.FollowDomain;
import com.sudasuda.app.domain.Link;
import com.sudasuda.app.domain.User;
import com.sudasuda.app.service.*;
import com.sudasuda.app.utils.CookieUtil;
import com.sudasuda.app.vo.NameCountVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "name", required = false) String name,
			Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(
			@RequestParam(value = "name", required = false) String name,
			HttpServletRequest request, Model model) {
		
		LinkService linkService = new LinkService();
		
		// Randomly chose a type of graph
		
		double randomNo = Math.random();
		randomNo = randomNo * 10;
		randomNo = randomNo % 5;
		
		randomNo = randomNo * -1;
		
		int rNo = (int) randomNo;
		
		if (rNo == 0) rNo = -1;
		
		logger.info("Random No = "+(rNo));
		List<NameCountVO> counts = linkService.getCategoryLinkCount(rNo,-1,"");
		
		request.setAttribute("counts", counts);

		return "welcome";
	}
	
	@RequestMapping(value = "/SignIn", method = RequestMethod.GET)
	public String signIn(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", required = false, defaultValue = "signin") String page) {
		request.setAttribute("tab", "");
		request.setAttribute("page", page);

		return "signin";
	}

	@RequestMapping(value = "/SignIn", method = RequestMethod.POST)
	public String signIn(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserService userService = new UserService();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		boolean remember = "on".equals(request.getParameter("remember"));
		
		logger.info("REMEMBER ME -------------->"+request.getParameter("remember"));
		
		boolean skip = false;
		request.setAttribute("tab", "");

		request.setAttribute("page", "signin");

		if (username != null && username.trim().length() == 0) {

			skip = true;
		}

		if (!skip && action != null && action.equalsIgnoreCase("login")) {
			// String emailreg =
			// "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

			// Boolean isValidEmail = email.matches(emailreg);
			logger.info("action = login for username = " + username);

			User user = userService.getUser(username);
			if (userService.authenticate(username, encryptPassword(password))) {
				request.getSession().setAttribute("userInfo", user);
				request.getSession().setMaxInactiveInterval(60 * 60);
				System.out.println("remember ^^^^^^^^^^^^^^^^^^^^^^^:"
						+ remember);
				if (remember) {
					String uuid = UUID.randomUUID().toString();
					userService.saveUUID(uuid, user.getUserName());
					CookieUtil.addCookie(response, CookieUtil.COOKIE_NAME,
							uuid, 2592000);
				} else {
					userService.deleteUUID(user.getUserName());
					CookieUtil.removeCookie(response, CookieUtil.COOKIE_NAME);
				}

				response.sendRedirect(request.getContextPath()
						+ "/GetLinks?tab=current");
			} else {
				request.getSession().removeAttribute("userInfo");
				request.setAttribute("error", "Invalid username or password.  Please retry.");
				// response.sendRedirect(request.getContextPath()
					//	+ "/SignIn?page=signin");
				request.setAttribute("page", "signin");
				return "signin";
			}
		} else if (!skip) {
			String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			String usernameRegExp = "^[a-zA-Z0-9_-]{6,14}$";
			
			Boolean isValidUserName = username.matches(usernameRegExp);
			Boolean isValidEmail = email.matches(emailreg);
			request.setAttribute("page", "register");
			
			if (!isValidUserName) {
				request.setAttribute("error", "Username "+username+" is not valid");
				return "signin";
			}
			
			if (!isValidEmail){
				request.setAttribute("error", "Email: "+email+" is invalid");
				return "signin";
			}
		
			User user = userService.getUser(username);
			if ( isValidUserName && user != null && user.getUserName() != null )
			{
				request.setAttribute("error", "Username " + username + " is already taken");
				request.setAttribute("page","register");
				return "signin";
				
			}
			
			user = userService.getUserByEmail(email);
			if ( isValidEmail && user != null && user.getEmail() != null )
			{
				request.setAttribute("error", "Duplicate email address");
				request.setAttribute("page","register");
				return "signin";
			}
			
			
			

			
			if (user == null || user.getUserName() == null
					|| username.trim().length() != 0) {
				if (isValidEmail && isValidUserName) {
					userService.addUser(username, email,
							encryptPassword(password));
					request.getSession().setAttribute("userInfo",
							userService.getUser(username));
				}
				response.sendRedirect(request.getContextPath()
						+ "/GetLinks?tab=current");
				// return "signin";
			} else if (!skip) {
				if ( request.getAttribute("error") == null )
				request.setAttribute("error", "Username " + username + " is already taken");

				return "signin";
			}
		}

		if (skip) {
			response.sendRedirect(request.getContextPath()
					+ "/GetLinks?tab=current");

		}

		return "signin";

	}

	public String encryptPassword(String password) {
		String passwordToHash = password;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);
		return generatedPassword;
	}

	@RequestMapping(value = "/SignOut", method = RequestMethod.GET)
	public String signOut(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserService userService = new UserService();

		User user = (User) request.getSession().getAttribute("userInfo");

		if (user != null)
			userService.deleteUUID(user.getUserName());
		request.getSession().removeAttribute("userInfo");

		// response.sendRedirect(request.getContextPath()
			//	+ "/GetLinks?tab=current");
		request.setAttribute("tab", "");
		request.setAttribute("page", "signin");
		return "signin";

	}

	@RequestMapping(value = "/VoteUp", method = RequestMethod.GET)
	public void voteUp(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "linkId", required = false) int linkId,
			@RequestParam(value = "tab", required = false, defaultValue = "current") String tab)
			throws IOException {
	
		if ( request.getSession().getAttribute("userInfo") == null )
			response.sendRedirect(request.getContextPath() + "/GetLinks?tab=" + tab);
		
		User user = (User) request.getSession().getAttribute("userInfo");
		
		LinkService linkService = new LinkService();

		linkService.voteUpLink(linkId, user.getUserId());

		response.sendRedirect(request.getContextPath() + "/GetLinks?tab=" + tab);
	}

	@RequestMapping(value = "/CommentVoteUp", method = RequestMethod.GET)
	public void commentVoteUp(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "commentId", required = true) int commentId,
			@RequestParam(value = "linkId", required = true) int linkId,
			@RequestParam(value = "tab", required = false, defaultValue = "current") String tab)
			throws IOException {
		User user = (User) request.getSession().getAttribute("userInfo");
		CommentService commentService = new CommentService();

		commentService.voteUpComment(commentId, user.getUserId());

		response.sendRedirect(request.getContextPath() + "/AddComment?linkId=" + linkId);
	}
	
	@RequestMapping(value = "/AddLink", method = RequestMethod.GET)
	public String addLink(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		request.setAttribute("tab", "new");
		return "addlink";
	}

	@RequestMapping(value = "/Spam", method = RequestMethod.GET)
	public RedirectView spam(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value="tab", required=false, defaultValue="Current") String tab) {

		LinkService linkService = new LinkService();
		User user = (User) request.getSession().getAttribute("userInfo");
		String linkId = request.getParameter("linkId");

		logger.info("Link id=" + linkId + " being reported as spam by "
				+ user.getUserName());

		if (user != null && linkId != null) {
			linkService.addSpam(Integer.valueOf(linkId), user);
		}

		RedirectView view = new RedirectView(request.getContextPath()
				+ "/GetLinks?tab="+tab);

		return view;
	}

	@RequestMapping(value = "/AddLink", method = RequestMethod.POST)
	public String processAddLink(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		LinkService linkService = new LinkService();
		request.setCharacterEncoding("UTF-8");

		String url = request.getParameter("url");
		String title = URLDecoder
				.decode(request.getParameter("title"), "UTF-8");
		String media_type = request.getParameter("media");
		String tags[] = request.getParameter("tags").split(",");
		
		// Tags implementation: http://stackoverflow.com/questions/1810356/how-to-implement-tag-system
		
		

		logger.info("Title ->" + title);
		logger.info("URL -> " + url);
		logger.info("Tags ->" + tags);

		User user = (User) request.getSession().getAttribute("userInfo");
		boolean skip = false;

		if (user != null && url != null && url.trim().length() > 0
				&& title != null && title.trim().length() > 0) {

			if (!linkService.isLinkPresent(url))
				linkService.addLink(url, title, "" + user.getUserId(),
						request.getParameter("language"),
						request.getParameter("category"), request.getParameter("country"), media_type,tags);
			else {
				request.setAttribute("error", "The item is already submitted");
				skip = true;
				return "addlink";
			}

		}

		if (!skip)
			response.sendRedirect(request.getContextPath() + "/GetLinks?tab=New");

		return "addlink";
	}

	@RequestMapping(value = "/FollowUserManager", method = RequestMethod.GET)
	public String followUsers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LinkService linkService = new LinkService();
		UserService userService = new UserService();
		FollowUserService followUserService = new FollowUserService();
		
		String op = request.getParameter("op");
		String followUserName = request.getParameter("submittedBy");
		List <Link> links = null;
		
		User user = (User) request.getSession().getAttribute("userInfo");
		request.setAttribute("tab", "");
		
		if ( op != null && op.equalsIgnoreCase("add") && user != null )
		{
			followUserService.addFollowUser(user.getUserId(), followUserName);
		}
		
		if ( op != null && op.equalsIgnoreCase("remove") && user != null )
		{
			followUserService.removeFollowUser(user.getUserId(), followUserName);
		}
		
		User submittedBy = userService.getUser(request.getParameter("submittedBy")+"");
		links = linkService.getSubmittedByLinks((submittedBy != null? submittedBy.getUserId():0));
		if (user != null) {
			request.setAttribute("isFollowAllowed", followUserService.isFollowAllowed(user.getUserId(), followUserName));
			request.setAttribute("followUsers", followUserService.getFollowUsers(user.getUserId()));
		}
		request.setAttribute("links", links);
		
		return "linksSubmittedBy";
		
	}
	
	@RequestMapping(value = "/FollowManager", method = RequestMethod.GET)
	public String followers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LinkService linkService = new LinkService();
		FollowDomainService followDomainService = new FollowDomainService();
		UserService userService = new UserService();

		String op = request.getParameter("op");
		String domain = request.getParameter("domain");
		List<Link> links = null;
		List<FollowDomain> domains = null;
		User user = (User) request.getSession().getAttribute("userInfo");
		request.setAttribute("tab", "current");

		if (op != null && op.equalsIgnoreCase("add") && user != null) {
			followDomainService.addFollowDomain(domain, user.getUserId());
		}

		if (op != null && op.equalsIgnoreCase("remove") && user != null) {
			followDomainService.removeFollowDomain(domain, user.getUserId());
		}

		links = linkService.getDomainLinks(request.getParameter("domain"));
		if (user != null)
			domains = followDomainService.getFollowDomains(user.getUserId());

		request.setAttribute("topPosters", userService
				.getTopPostersForDomain(request.getParameter("domain")));

		request.setAttribute("domains", domains);
		request.setAttribute("links", links);
		if (user != null)
			request.setAttribute(
					"isFollowAllowed",
					followDomainService.isFollowAllowed(domain,
							user.getUserId()));

		return "domainLinks";
	}

	@RequestMapping(value = "/AddComment", method = RequestMethod.GET)
	public String getComment(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "linkId", required = false) String linkId) {
		CommentService commentService = new CommentService();
		LinkService linkService = new LinkService();
		
		User user = (User) request.getSession().getAttribute("userInfo");

		request.setAttribute("comments",
				commentService.getComments(Integer.valueOf(linkId), user));
		Link link = linkService.getLink(Integer.valueOf(linkId));

		request.setAttribute("text", "");
		request.setAttribute("linkId", linkId);
		request.setAttribute("link", link);

		return "comment";
	}
	
	@RequestMapping(value = "/Analytics", method = RequestMethod.GET)
	public String getAnalytics(HttpServletRequest request,
			HttpServletResponse response) {
		
		LinkService linkService = new LinkService();

		User user = (User) request.getSession().getAttribute("userInfo");
		List <NameCountVO> categoryVOs = null;
		
		int type = 0;
		
		if ( request.getParameter("type") != null ) type = Integer.valueOf(request.getParameter("type"));
		
		if ( user != null )
		{
			String cond = "";
			if ( type == 3 ) cond = request.getParameter("source");
			categoryVOs = linkService.getCategoryLinkCount(user.getUserId(),type,cond);
			
		}
		
		if ( type == 0 ) {
			request.setAttribute("header", " item likes by category");
			request.setAttribute("type", "category");
		}
		
		if ( type == 1 ) {
						 request.setAttribute("header", " item likes by source");
						 request.setAttribute("type", "source");
		}
		
		if ( type == 3 ) {
			request.setAttribute("header", " item likes by category for source "+request.getParameter("source"));
			request.setAttribute("type", "category");
		}
		
		request.setAttribute("text", "");
		request.setAttribute("counts", categoryVOs);

		return "analytics";
	}


	@RequestMapping(value = "/AddComment", method = RequestMethod.POST)
	public String addComment(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "linkId", required = false) String linkId) throws UnsupportedEncodingException {
		CommentService commentService = new CommentService();
		LinkService linkService = new LinkService();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		User user = (User) request.getSession().getAttribute("userInfo");
		if (user != null && request.getParameter("comment") != null
				&& request.getParameter("comment").trim().length() > 0) {
			commentService.addComment(user.getUserId(),
					Integer.valueOf(linkId), request.getParameter("comment"));
		}

		Link link = linkService.getLink(Integer.valueOf(linkId));
		request.setAttribute("link", link);
		request.setAttribute("comments",
				commentService.getComments(Integer.valueOf(linkId), user));
		request.setAttribute("linkId", linkId);
		
		return "comment";
	}

	@RequestMapping(value = "/GetLinks", method = RequestMethod.GET)
	public String getLinks(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "tab", required = false, defaultValue = "Current") String tab,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "country", required = false) String country,
			HttpSession session) {
		logger.info("GetLinks Invoked");

		UserService userService = new UserService();
		LinkService linkService = new LinkService();
		FollowDomainService followDomainService = new FollowDomainService();
		FollowUserService followUserService = new FollowUserService();

		List<Link> links = null;
		List<FollowDomain> domains = null;
		List<NameCountVO> tags = null;
		User user = null;

		// Just in case if someone tries to crash the page
		
		if ( tab != null && !(tab.equalsIgnoreCase("Current") 
							|| tab.equalsIgnoreCase("New") 
							|| tab.equalsIgnoreCase("Expired")
							|| tab.equalsIgnoreCase("MyLinks")))
			tab="Current";
		
		// request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		request.setAttribute("tab", tab);

		if (lang != null)
			session.setAttribute("language", lang);
		if (category != null)
			session.setAttribute("category", category);
		if (country != null )
			session.setAttribute("country", country);

		if (lang == null && session.getAttribute("language") == null)
			session.setAttribute("language", "All");
		if (category == null && session.getAttribute("category") == null)
			session.setAttribute("category", "All");
		if (country == null && session.getAttribute("country") == null)
			session.setAttribute("country", "All");
		
		lang = session.getAttribute("language").toString();
		category = session.getAttribute("category").toString();
		country = session.getAttribute("country").toString();
		// session.setAttribute("language", lang);
		// session.setAttribute("category", category);

		if (session.getAttribute("userInfo") == null) {
			checkCookie(request, response);
		}
		
		List<String> recentSubmitters = linkService.getRecentSubmitters();
		StringBuilder submittersStr = new StringBuilder();
		
		for(String submitter : recentSubmitters) {
			if ( submittersStr.length() > 0)
			submittersStr.append(", ");
			submittersStr.append(submitter);
		}
		
		request.setAttribute("recentSubmitters", "Recent Submitters: "+submittersStr);
			
		
	//	session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("ta"));

		if (session.getAttribute("userInfo") != null) {
			user = (User) request.getSession().getAttribute("userInfo");
			domains = followDomainService.getFollowDomains(user.getUserId());
		}

		if (request.getParameter("submittedBy") == null
				&& request.getParameter("domain") == null) {

			if (tab.equalsIgnoreCase("current"))
				links = linkService.getLinks((user != null ? user.getUserId()
						: 0), lang, category, country);
			if (tab.equalsIgnoreCase("new"))
				links = linkService.getNewLinks(
						(user != null ? user.getUserId() : 0), lang, category, country);
			if (tab.equalsIgnoreCase("expired"))
				links = linkService.getExpiredLinks(
						(user != null ? user.getUserId() : 0), lang, category, country);
			if (tab.equalsIgnoreCase("mylinks")  && user != null) {
				links = linkService.getMyLinks(user.getUserId(), lang, category, country);
				tags = linkService.getMyTags(user.getUserId());
				request.setAttribute("tags", tags);
			}

		} else if (request.getParameter("submittedBy") != null) {
			System.out.println("Submitted By page");
			User submittedBy = userService.getUser(request
					.getParameter("submittedBy") + "");
			links = linkService
					.getSubmittedByLinks((submittedBy != null ? submittedBy
							.getUserId() : 0));
			request.setAttribute(
					"isFollowAllowed",
					followUserService.isFollowAllowed(
							(user != null ? user.getUserId() : 0),
							submittedBy.getUserName()));
			request.setAttribute("followUsers", followUserService
					.getFollowUsers((user != null ? user.getUserId() : 0)));
			request.setAttribute("tab", "");
		}

		if (request.getParameter("domain") != null) {
			System.out.println("Domainn page");
			links = linkService.getDomainLinks(request.getParameter("domain"));
			if (user != null)
				request.setAttribute(
						"isFollowAllowed",
						followDomainService.isFollowAllowed(
								request.getParameter("domain"),
								user.getUserId()));
			request.setAttribute("topPosters", userService
					.getTopPostersForDomain(request.getParameter("domain")));
			request.setAttribute("tab", "");
		}

		request.setAttribute("domains", domains);
		request.setAttribute("links", links);
		

		if (request.getParameter("submittedBy") == null
				&& request.getParameter("domain") == null) {

			return "getlinks";
		}
		if (request.getParameter("submittedBy") != null) {
			return "linksSubmittedBy";
		}
		if (request.getParameter("domain") != null) {
			return "domainLinks";
		}

		return "getlinks";
	}

	private void checkCookie(HttpServletRequest req, HttpServletResponse res) {
		String uuid = CookieUtil.getCookieValue(req, CookieUtil.COOKIE_NAME);
		logger.info("uuid=" + uuid);

		UserService userService = new UserService();
		User user = null;

		logger.info("GetLinks: finding cookie");

		if (uuid != null) {
			user = userService.findUUID(uuid);

			if (user != null) {
				req.getSession().setAttribute("userInfo", user);
				CookieUtil
						.addCookie(res, CookieUtil.COOKIE_NAME, uuid, 2592000);
			} else {
				CookieUtil.removeCookie(res, CookieUtil.COOKIE_NAME);
			}

		}
	}

}
