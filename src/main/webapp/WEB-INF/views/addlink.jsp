<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List,com.sudasuda.app.domain.Link,com.sudasuda.app.domain.User, java.net.URLDecoder"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!--  link rel="shortcut icon" href="../../assets/ico/favicon.ico" -->

<title><spring:message code="label.title" /></title>

<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/resources/css/navbar.css"
	rel="stylesheet">

<link href="<%=request.getContextPath()%>/resources/css/bootstrap-tagsinput.css"
	rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
.auto-style1 {
	font-family: "Arial Black";
	font-size: xx-large;
}

.auto-style2 {
	text-align: center;
	color: #6868F8;
	border-left-style: none;
	border-left-width: medium;
	border-right-style: solid;
	border-right-width: 0px;
	border-top-style: solid;
	border-top-width: 0px;
	border-bottom-style: solid;
	border-bottom-width: 0px;
}

.auto-style3 {
	font-family: "Arial Black";
	font-weight: bold;
	font-size: xx-large;
	color: #F43402;
}

.auto-style4 {
	border-width: 0px;
}

.auto-style5 {
	border-left-style: solid;
	border-left-width: 0px;
	border-right-style: none;
	border-right-width: medium;
	border-top-style: none;
	border-top-width: medium;
	border-bottom-style: solid;
	border-bottom-width: 0px;
	font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
		sans-serif;
	font-size: medium;
}

.auto-style6 {
	border-left-style: solid;
	border-left-width: 0px;
	border-right-style: none;
	border-right-width: medium;
	border-top-style: solid;
	border-top-width: 0px;
	border-bottom-style: solid;
	border-bottom-width: 0px;
}

.auto-style7 {
	border-left-style: solid;
	border-left-width: 0px;
	border-right-style: none;
	border-right-width: medium;
	border-top-style: none;
	border-top-width: medium;
	border-bottom-style: solid;
	border-bottom-width: 0px;
	font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS",
		sans-serif;
	font-size: small;
}

.auto-style8 {
	border-left-style: solid;
	border-left-width: 0px;
	border-right-style: none;
	border-right-width: medium;
	border-top-style: solid;
	border-top-width: 0px;
	border-bottom-style: none;
	border-bottom-width: medium;
}

.auto-style9 {
	text-align: center;
	color: #6868F8;
	border-left-style: none;
	border-left-width: medium;
	border-right-style: solid;
	border-right-width: 0px;
	border-top-style: none;
	border-top-width: medium;
	border-bottom-style: solid;
	border-bottom-width: 0px;
	font-size: x-large;
}

.auto-style-links {
	A: link{color:#FF0000;
}

A:visited {
	color: #0000FF;
}

}
.auto-style14 {
	font-size: x-small;
}

.auto-style16 {
	font-size: x-small;
	color: black;
}
</style>

</head>

<body style="background-color: <spring:message code='background.color' />">
	<table style="width: 1352px; height: 20px">
		<tr>
			<td class="auto-style3" style="width: 1100px"></td>
			<td class="auto-style7">&nbsp;</td>
		</tr>
		<tr>
			<td class="auto-style3" style="width: 1066px"></td>
			<td><br /></td>
		</tr>
	</table>


	<div class="container">

		<!-- Static navbar -->
		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid" style="background-color: <spring:message code='background.color' />;">
				<div class="navbar-header" style="background-color: <spring:message code='background.color' />;">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" style="color: #FF8000"
						href="<%=request.getContextPath()%>/about?homepage=yes"><spring:message code="label.title" /></a>
				</div>
				<%
					request.setAttribute("tab", "");
				%>
				<div class="navbar-collapse collapse"
					style="background-color: <spring:message code='background.color' />;">
					<ul class="nav navbar-nav" style="background-color: <spring:message code='background.color' />;">
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("current") ? "class='active'" : "")%>><a
							style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=Current"><spring:message code="label.trending" /></a></li>
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("new") ? "class='active'" : "")%>><a
							style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=New"><spring:message code="label.new" /></a></li>
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("expired") ? "class='active'" : "")%>><a
							style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=Expired"><spring:message code="label.expired" /></a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							style="color: #01B6AD;" data-toggle="dropdown"><spring:message code="label.more" /> <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<%-- <li><a href="<%=request.getContextPath()%>/bookmarklet">Bookmarklet</a></li> --%>
								<li><a
									href="<%=request.getContextPath()%>/about?homepage=no">About
										Us</a></li>
								<%--   <li><a href="#">Something else here</a></li>
                  <li class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>  --%>
							</ul></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<%
							if (request.getSession().getAttribute("userInfo") == null) {
						%>
						<li><a style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/SignIn?page=signin">Sign
								In</a></li>
						<%
							} else {
								User user = (User) request.getSession()
										.getAttribute("userInfo");
						%>
						<li><a style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/SignOut">Sign Out (<%=user.getUserName()%>)
						</a></li>
						<%
							}
						%>
						<!--       <li class="active"><a href="./">Default</a></li>
              <li><a href="../navbar-static-top/">Static top</a></li>
              <li><a href="../navbar-fixed-top/">Fixed top</a></li> -->
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</div>


		<br />
		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron" style="background-color: <spring:message code='background.color.jumbotron' />; color: <spring:message code='addlink.text.color' />;">
			<form class="form-horizontal"
				action="<%=request.getContextPath()%>/AddLink" method="POST">
				<fieldset>

					<!-- Form Name -->
					<span style="color: <spring:message code='addlink.text.color' />; font-size: large;" >Submit a new item </span>
				
				<%if ( request.getAttribute("error") != null ) { %>
				<p style="color: red; font-size: small;"><%=request.getAttribute("error") %></p>
				<%} %>
					<!-- Text input-->
					<div class="form-group">
						<label class="col-md-3 control-label" for="title">Title</label>
						<div class="col-md-8">
							<input id="title" name="title" placeholder="Title"
								class="form-control input-md" required="" type="text" value='<%=(request.getParameter("t") != null? request.getParameter("t"):"")%>'>

						</div>
					</div>

					<!-- Text input-->
					<div class="form-group">
						<label class="col-md-3 control-label" for="url">URL</label>
						<div class="col-md-8">
							<input id="url" name="url" placeholder="URL"
								class="form-control input-md" required="" type="text" value='<%=(request.getParameter("u") != null? request.getParameter("u"):"")%>' >

						</div>
					</div>
					
					<!-- Text input-->
				<%--	<div class="form-group">
						<label class="col-md-3 control-label" for="price">List Price</label>
						<div class="col-md-4">
							<input id="url" name="url" placeholder="1000000.00"
								class="form-control input-md" required="" type="text" value='<%=(request.getParameter("u") != null? request.getParameter("u"):"")%>' >

						</div>
					</div>  --%>
					

					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-md-3 control-label" for="category">Category</label>
						<div class="col-md-3">
							<select id="category" name="category" class="form-control">
								<option value="Technology">Technology</option>
								<option value="Business">Business</option>
								<option value="Finance">Finance</option>
								<option value="Education">Education</option>
								<option value="Marketing">Marketing</option>
								<option value="Other">Other</option>
							</select>
						</div>
					</div>

					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-md-3 control-label" for="language">Language</label>
						<div class="col-md-3">
							<select id="language" name="language" class="form-control">
								<option value="English">English</option>
								<option value="Tamil">தமிழ்</option>
								<option value="Hindi">हिन्दी</option>
							</select>
						</div>
					</div>

					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-md-3 control-label" for="media">Media</label>
						<div class="col-md-3">
							<select id="media" name="media" class="form-control">
								<option value="Text / HTML">Text / HTML</option>
								<option value="Audio / Video">Audio / Video</option>
								<option value="Photo">Photo</option>
							</select>
						</div>
					</div>
					
					<!-- Select Basic -->
					<div class="form-group">
						<label class="col-md-3 control-label" for="country">Country</label>
						<div class="col-md-3">
							<select id="country" name="country" class="form-control">
								<option value="India">India</option>
								<option value="Malaysia">Malaysia</option>
								<option value="Singapore">Singapore</option>
								<option value="Sri Lanka">Sri Lanka</option>
								<option value="United States">United States</option>
								<option value="United Kingdom">United Kingdom</option>
								<option value="UAE">UAE</option>
								<option value="World">World</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-3 control-label" for="singlebutton">Tags</label>
						<div class="col-md-5">
							<input id="tags" name="tags" type="text" value="" data-role="tagsinput" placeholder="Add tags" />
						</div>
					</div>
						

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-3 control-label" for="singlebutton">&nbsp;</label>
						<div class="col-md-3">
							<button id="singlebutton" name="singlebutton"
								class="btn btn-primary">Submit</button>
						</div>
					</div>

				</fieldset>
			</form>
		</div>

	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap-tagsinput.min.js"></script>
	<script type="text/javascript">
		var sc_project = 9681219;
		var sc_invisible = 1;
		var sc_security = "9ea9e173";
		var scJsHost = (("https:" == document.location.protocol) ? "https://secure."
				: "http://www.");
		document
				.write("<sc"+"ript type='text/javascript' src='" +
scJsHost+
"statcounter.com/counter/counter.js'></"+"script>");
	</script>
	<noscript>
		<div class="statcounter">
			<a title="click tracking" href="http://statcounter.com/"
				target="_blank"><img class="statcounter"
				src="http://c.statcounter.com/9681219/0/9ea9e173/1/"
				alt="click tracking"></a>
		</div>
	</noscript>
	<!-- End of StatCounter Code for Default Guide -->
</body>
</html>
