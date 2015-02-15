<%@ page import="java.util.List,com.sudasuda.app.domain.Link,com.sudasuda.app.domain.User, java.net.URLDecoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<title>சுடசுட.com</title>

<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/resources/css/navbar.css"
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
	<td class="auto-style7"><% if ( request.getSession().getAttribute("userInfo") != null ) { User user = (User) session.getAttribute("userInfo"); %>
		<%= user.getUserName() %> <%} %><% else { %> &nbsp; <%} %>
		</td>
</tr>
<tr>
	<td class="auto-style3" style="width: 1066px"></td>
	<td><br/></td>	
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
						href="<%=request.getContextPath()%>/about?homepage=yes">சுடசுட.com</a>
				</div>
				<% request.setAttribute("tab",""); %>				
				<div class="navbar-collapse collapse" style="background-color: <spring:message code='background.color' />;">
					<ul class="nav navbar-nav" style="background-color: <spring:message code='background.color' />;">
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("current") ? "class='active'" : "")%>><a style="color:#01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=Current">Trending</a></li>
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("new") ? "class='active'" : "")%>><a style="color:#01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=New">New</a></li>
						<li
							<%=(request.getAttribute("tab").toString()
					.equalsIgnoreCase("expired") ? "class='active'" : "")%>><a style="color:#01B6AD;"
							href="<%=request.getContextPath()%>/GetLinks?tab=Expired">Expired</a></li>
						
						<li class="dropdown"><a style="color:#01B6AD;" href="#" class="dropdown-toggle"
							data-toggle="dropdown">More <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Bookmarklet</a></li>
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
						if ( request.getSession().getAttribute("userInfo") == null ) {
						%>
						<li><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/SignIn?page=signin">Sign
								In</a></li>
						<%
							} else { User user = (User) request.getSession().getAttribute("userInfo");
						%>
						<li><a style="color:#01B6AD;"
							href="<%=request.getContextPath()%>/SignOut">Sign
								Out(<%=user.getUserName()%>)</a></li>
						<%
							}
						%>
        <%--      <li class="active"><a href="./">Default</a></li>
              <li><a href="../navbar-static-top/">Static top</a></li>
              <li><a href="../navbar-fixed-top/">Fixed top</a></li> --%>
            </ul>  
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</div>


		<br />
		<!-- Main component for a primary marketing message or call to action -->
		<%-- <div class="jumbotron"> --%>

		
<table cellpadding="10" style="width: 1352px; height: 100px" class="auto-style4">
	<tr>
		<td style="width: 50px; height: 40px" class="auto-style5"></td>
		<td style="height: 40px; color: white;" class="auto-style5">To install the bookmarklet just drag-n-drop the link to browser toolbar</td>
		<td style="height: 40px" class="auto-style5">&nbsp;</td>
	</tr>
	<tr>
		<td style="width: 50px; height: 40px" class="auto-style5"></td>
		<td style="height: 40px" class="auto-style5">
		<a href="javascript:window.location=%22http://www.sudasuda.com/AddLink?u=%22+unescape(encodeURIComponent(document.location))+%22&t=%22+encodeURIComponent(document.title)">
		 post to சுடசுட.com
		</a></td>
		<td style="height: 40px" class="auto-style5">&nbsp;</td>
	</tr>

	</table>

			<%--        <h1>Navbar example</h1>
        <p>This example is a quick exercise to illustrate how the default, static navbar and fixed to top navbar work. It includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>
        <p>
          <a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">View navbar docs &raquo;</a>
        </p> 
        
        --%>
		</div>

	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
var sc_project=9681219; 
var sc_invisible=1; 
var sc_security="9ea9e173"; 
var scJsHost = (("https:" == document.location.protocol) ?
"https://secure." : "http://www.");
document.write("<sc"+"ript type='text/javascript' src='" +
scJsHost+
"statcounter.com/counter/counter.js'></"+"script>");
</script>
<noscript><div class="statcounter"><a title="click tracking"
href="http://statcounter.com/" target="_blank"><img
class="statcounter"
src="http://c.statcounter.com/9681219/0/9ea9e173/1/"
alt="click tracking"></a></div></noscript>
<!-- End of StatCounter Code for Default Guide -->
</body>
</html>
