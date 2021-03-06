<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.sudasuda.app.domain.Link,com.sudasuda.app.domain.User,com.sudasuda.app.domain.FollowDomain" %>
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

    <title><spring:message code="label.title" /></title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/resources/css/grid.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/resources/css/navbar.css" rel="stylesheet">

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
	font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
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
	font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
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
	A:link {color:#FF0000;}
	A:visited {color:#0000FF;}
}
.auto-style14 {
	font-size: x-small;
} 
.auto-style16 {
	font-size: x-small;
	color: black;
}

a.storystyle:link {}
.storystyle a:LINK {
	color: <spring:message code='link.color' />;
}

a.storystyle:visited {}
.storystyle a:VISITED {
	color: <spring:message code='link.color' />;
}

a.storystyle:hover {background-color: #0000FF}
.storystyle a:HOVER {
	color: <spring:message code='link.hoover.color' />;
	background-color: <spring:message code='link.hoover.background.color' />;
}

</style> 
    
  </head>

<body style="background-color: <spring:message code='background.color' />">
  <table style="width: 1352px; height: 20px">
<tr>
	<td class="auto-style3" style="width: 1100px"></td>
	<td class="auto-style7"><% if ( request.getSession().getAttribute("userInfo") != null ) { User user = (User) session.getAttribute("userInfo"); %>
		<%= user.getUserName() %> <%} else { %> &nbsp; <%} %> 
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
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color:#FF8000" href="<%=request.getContextPath()%>/about?homepage=yes">சுடசுட.com</a>
          </div>
          <div class="navbar-collapse collapse" style="background-color: <spring:message code='background.color' />;">
            <ul class="nav navbar-nav" style="background-color: <spring:message code='background.color' />;">
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("current")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=Current"><spring:message code="label.trending" /></a></li>
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("new")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=New"><spring:message code="label.new" /></a></li>
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("expired")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=Expired"><spring:message code="label.expired" /></a></li>
          	  
          <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color:#01B6AD;"><spring:message code="label.more" /> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/Analytics">Analytics</a></li>
                  <li><a href="<%=request.getContextPath()%>/bookmarklet">Bookmarklet</a></li>
                  <li><a href="<%=request.getContextPath()%>/about?homepage=no">About Us</a></li>
               <%--   <li><a href="#">Something else here</a></li>
                  <li class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>  --%>
                </ul>
              </li>  
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
  <%--            <li class="active"><a href="./">Default</a></li>
              <li><a href="../navbar-static-top/">Static top</a></li>
              <li><a href="../navbar-fixed-top/">Fixed top</a></li> --%>
            </ul>  
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </div>

<table cellpadding="10" style="width: 1352px; height: 20px" class="auto-style4"> 
	<tr>
		<td style="width: 20px; height: 40px" class="auto-style5">
		<% List<FollowDomain> domains = (List<FollowDomain>)request.getAttribute("domains"); if ( domains != null && domains.size()>0) { %>
			<select name="follow" width="20" onchange="javascript: changePage(this.value);">
		<%--	<option value="Please select">Please select....</option> --%>
		<%  String currentDomain = request.getParameter("domain");
			for ( int i =0; domains != null && i<domains.size(); i++) {
				FollowDomain domain = domains.get(i);
				%>
				<option value="<%=domain.getDomain()%>"  <%=(currentDomain.equalsIgnoreCase(domain.getDomain())?" selected":"")%>><%=domain.getDomain()%></option>
			<%}%>
		
		 </select> <%} %>
		</td>
		<td style="width: 26px; height: 40px" class="auto-style5"></td>
		<td style="width: 20px; height: 40px" class="auto-style9"></td>
		<td style="height: 40px; color: blue" class="auto-style5"> 
		Stories from source: <%=request.getParameter("domain")%> <% User user = (User) session.getAttribute("userInfo"); String domain=request.getParameter("domain"); Boolean isFollow=(request.getAttribute("isFollowAllowed") != null? (Boolean) request.getAttribute("isFollowAllowed"):false); if (isFollow && user != null) { %>
		<span class="auto-style15">(<a href="<%=request.getContextPath()%>/FollowManager?op=add&domain=<%=domain%>">Follow</a>)</span>
		<% } if (!isFollow && user != null){ %><span class="auto-style15">(<a href="<%=request.getContextPath()%>/FollowManager?op=remove&domain=<%=domain%>">Unfollow</a>)</span><%} %>
		</td>
		<td style="height: 40px" class="auto-style5">
		</td>
		
	</tr>

	</table>

	<br/>
      <!-- Main component for a primary marketing message or call to action -->
    <%--  <div class="jumbotron"> --%>
<%--        <h1>Navbar example</h1>
        <p>This example is a quick exercise to illustrate how the default, static navbar and fixed to top navbar work. It includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>
        <p>
          <a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">View navbar docs &raquo;</a>
        </p> --%>
        
<table cellpadding="10" style="width: 1352px; height: 20px" class="auto-style4">
	<% 
	List <Link> links = (List<Link>) request.getAttribute("links"); 
	for (int i = 0; links != null &&  i < links.size(); i++) { 
		Link link = links.get(i);
	%>
<div class="row">
        <div class="col-md-1" style="text-align: center; background-color: <spring:message code='background.color.row' />; color: #FFFFFF;"><%=link.getVotes()%><br/><%=(link.getVotes()<=1?"Like":"Likes")%>
       <% if ( link.getTitle().length() >= 180 ){ %><br />&nbsp;<%} %>
        </div>
        <div class="col-md-11 storystyle" style="background-color: <spring:message code='background.color.row' />;"><a href='<%=link.getUrl()%>' target="_blank" style="text-decoration: none">
		<%= link.getTitle() %></a> 
		<%--<span class="auto-style16">(<a href="<%=request.getContextPath()%>/GetLinks?domain=<%=link.getDomain()%>"><%=link.getDomain()%></a>)</span> --%>
		<br />
		<span class="auto-style14">submitted by: <a href="<%=request.getContextPath()%>/GetLinks?submittedBy=<%=link.getSubmitedBy()%>">
		<%= link.getSubmitedBy()%></a><%=" "+link.getHoursElapsed()+" hours ago " %>|&nbsp;<a href="<%=request.getContextPath()%>/AddComment?linkId=<%=link.getLinkId()%>"><%=link.getNoOfComments()+" comments" %>
		</a> |&nbsp;<a href="<%=request.getContextPath()%>/Spam?linkId=<%=link.getLinkId()%>&tab=<%=request.getAttribute("tab")%>">Report(<%=link.getSpam()%>)</a></span>
		</div>
</div>
	<% } %>
<tr>
		<td style="width: 50px; height: 40px" class="auto-style9">
		</td>
		<td style="width: 50px; height: 40px" class="auto-style9">&nbsp;</td>
		<td style="width: 50px; height: 40px" class="auto-style9"></td>
		<td style="height: 40px; color: blue" class="auto-style5"> 
		<td style="height: 40px" class="auto-style5">
		</td>
		
	</tr>
</table>
		<br/>
	<div style="color: #FFFFFF;">	Top submitters for <%=request.getParameter("domain")%>:</div><span class="auto-style15"> <%List<User> users = (List<User>) request.getAttribute("topPosters"); 
	  for (int i=0; i < users.size(); i++ ) { User topPoster = users.get(i);%><a href="<%=request.getContextPath()%>/GetLinks?submittedBy=<%=topPoster.getUserName()%>"> <%=topPoster.getUserName()%></a>&nbsp; <%} %>
		</span>
       
        
        
      </div>

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
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
    <script type="text/javascript">
	function changePage(a)
	{
		if ( a != "Please select...." )
		location.href='<%=request.getContextPath()%>/GetLinks?domain='+a;	
	}
</script>
  </body>
</html>
