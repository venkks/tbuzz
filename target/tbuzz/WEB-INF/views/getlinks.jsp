<%@page import="org.springframework.web.servlet.i18n.SessionLocaleResolver"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.sudasuda.app.domain.Link,com.sudasuda.app.domain.User,com.sudasuda.app.vo.NameCountVO" %>
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

	<title><spring:message code="label.title" /> </title>
    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/resources/css/navbar.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/resources/css/grid.css" rel="stylesheet">

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

	
.auto-style14 {
	font-size: x-small;
}
.auto-style16 {
	font-size: x-small;
	color: black;
}

.glyphicon.glyphicon-thumbs-up{
	font-size: 2.5em;
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
  <%--
  <body background='<%=request.getContextPath()+"/resources/img/sudasuda_cover2.jpg"%>'>   --%>
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
	<td class="auto-style7">
		</td>
</tr>
</table>
  
    <div class="container">

      <!-- Static navbar -->
      <div class="navbar navbar-default navbar-fixed-top" role="navigation" style="background-color: <spring:message code='background.color' />;">
        <div class="container-fluid" style="background-color: <spring:message code='background.color' />;">
          <div class="navbar-header" style="background-color: <spring:message code='background.color' />;">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color: #FF8000" href="<%=request.getContextPath()%>/about"><spring:message code="label.title" /></a>
          </div>
          <div class="navbar-collapse collapse" style="background-color: <spring:message code='background.color' />;" >
            <ul class="nav navbar-nav" style="background-color: <spring:message code='background.color' />;">
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("current")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=Current"><spring:message code="label.trending" /></a></li>
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("new")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=New"><spring:message code="label.new" /></a></li>
              <li <%=(request.getAttribute("tab").toString().equalsIgnoreCase("expired")?"class='active'":"")%>><a style="color:#01B6AD;" href="<%=request.getContextPath()%>/GetLinks?tab=Expired"><spring:message code="label.expired" /></a></li>          
              <li class="dropdown">
           	    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #01B6AD; background-color: <spring:message code='background.color' />">
           	    <spring:message code="label.profile" /><b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=mylinks"><spring:message code="label.mylikes" /></a></li>
                  <li><a href="<%=request.getContextPath()%>/Analytics"><spring:message code="label.analytics" /></a></li>
                  <li><a href="<%=request.getContextPath()%>/Analytics?type=1">Source Analytics</a></li>
                </ul>
              </li>
              <li class="dropdown">
           	    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #01B6AD; background-color: <spring:message code='background.color' />">
           	    <spring:message code="label.language" />(<%=(session.getAttribute("language").toString().equalsIgnoreCase("Tamil")?"தமிழ்":session.getAttribute("language")) %>)<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&lang=All&ln=en">All</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&lang=English&ln=en">English</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&lang=Tamil&ln=ta">தமிழ்</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&lang=Hindi&ln=hi">हिन्दी</a></li>
                </ul>
              </li>
              <li class="dropdown">
           	    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #01B6AD; background-color: <spring:message code='background.color' />">
           	    <spring:message code="label.category" /> (<%=session.getAttribute("category")%>)<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=All">All</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Technology">Technology</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Business">Business</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Finance">Finance</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Education">Education</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Marketing">Marketing</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&category=Other">Other</a></li>
                </ul>
              </li>
              <li class="dropdown">
           	    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #01B6AD; background-color: <spring:message code='background.color' />">
           	    <spring:message code="label.country" /> (<%=session.getAttribute("country")%>)<b class="caret"></b></a>
                <ul class="dropdown-menu">
             	  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=All">All</a></li>
     			  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=Australia">Australia</a></li>	
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=India">India</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=Malaysia">Malaysia</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=Singapore">Singapore</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=Sri Lanka">Sri Lanka</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=United States">United States</a></li>
               	  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=United Kingdom">United Kingdom</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=UAE">UAE</a></li>
                  <li><a href="<%=request.getContextPath()%>/GetLinks?tab=<%=request.getAttribute("tab")%>&country=World">World</a></li>
                </ul>
              </li>  
             <li class="dropdown">
                <a href="#" style="color:#01B6AD;" class="dropdown-toggle" data-toggle="dropdown">More <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/bookmarklet.jsp">Bookmarklet</a></li>
                  <li><a href="<%=request.getContextPath()%>/about?homepage=no">About Us</a></li>
                </ul>
              </li>  
            </ul>
            <ul class="nav navbar-nav navbar-right">
                 <%		
						if ( request.getSession().getAttribute("userInfo") == null ) {
						%>
						<li><a style="color: #01B6AD;" href="<%=request.getContextPath()%>/SignIn?page=signin">Sign
								In</a></li>
						
						<li><a style="color: #01B6AD;" href="<%=request.getContextPath()%>/SignIn?page=register">Sign
								Up</a></li>		
						<%
							} else { User user = (User) request.getSession().getAttribute("userInfo");
						%>
						<li><a style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/SignOut">Sign
								Out (<%=user.getUserName()%>)</a></li>
						<%
							}
						%>
     
            </ul>  
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </div>

	<br/>&nbsp;
	<p style="text-align: center;">
	<a href="<%=request.getContextPath()%>/AddLink" class="btn btn-lg btn-primary" role="button">Add an item</a>
	</p>

	<br/>
      	<% 
	List <Link> links = (List<Link>) request.getAttribute("links"); 
    if ( links == null || links.size() == 0) { %>
    <div class="row">
        <div class="col-md-12 storystyle" style="background-color: <spring:message code='background.color.row' />; color: <spring:message code='text.color.row'/>;" >
		Sorry, no items are available for the selected criteria at present.
		</div>
</div>

<%}%>
<%
	  for (int i = 0; links != null &&  i < links.size(); i++) { 
		Link link = links.get(i);
		boolean extraLine = false;
		if ( link.getTitle().codePointAt(0) >= 2949 && (link.getTitle().length()+link.getDomain().length()) >= 124) extraLine=true;
		if ( link.getTitle().length() >= 140 ) extraLine = true;
	%>

<div class="row">
<% String tab = (String) request.getAttribute("tab");
   if ( tab != null && !tab.equalsIgnoreCase("mylinks") ) {
%>
        <div class="col-md-1" style="text-align: center; background-color: <spring:message code='background.color.row' />;">
		<% if ( session.getAttribute("userInfo") == null || ( !link.isVoted() && !request.getAttribute("tab").toString().equalsIgnoreCase("expired") ) ) { %>
		<a style="text-decoration: none;"   href="<%=request.getContextPath()%>/VoteUp?linkId=<%=link.getLinkId() %>&tab=<%=request.getAttribute("tab")%>"><span class="glyphicon glyphicon-thumbs-up" style="color: grey"></span>
		</a>
		<% } else { %><a href="#"><span class="glyphicon glyphicon-thumbs-up"></span>&nbsp;</a>
		 <%} if ( extraLine ){ %><br />&nbsp;<%} %> </div>
		<%} %>
        <div class="col-md-1" style="text-align: center; background-color: <spring:message code='background.color.row' />; color: <spring:message code='vote.color.row' />;"><%=link.getVotes()%><br/><%=(link.getVotes()<=1?"like":"likes")%><% if ( extraLine ){ %><br />&nbsp;<%} %> </div>
        <div class="col-md-8 storystyle" style="background-color: <spring:message code='background.color.row' />;"><a href='<%=link.getUrl()%>'  target="_blank" style="text-decoration: none; text-color: 000000;">
		<%= link.getTitle() %></a>
		<span class="auto-style16" style="color: <spring:message code='text.color.row' />;">(<a href="<%=request.getContextPath()%>/GetLinks?domain=<%=link.getDomain()%>"><%=link.getDomain()%></a>) </span>
		<br />
		<span class="auto-style14" style="color: <spring:message code='subtext.color.row' />;">submitted by: <a href="<%=request.getContextPath()%>/GetLinks?submittedBy=<%=link.getSubmitedBy()%>">
		<%= link.getSubmitedBy()%></a><%=" "+( link.getHoursElapsed()<=24?link.getHoursElapsed()+" hours ago ":link.getHoursElapsed()/24+" days ago ") %>|&nbsp;<a href="<%=request.getContextPath()%>/AddComment?linkId=<%=link.getLinkId()%>"><%=link.getNoOfComments()+" comments" %>
		</a> |&nbsp;<a title="After 4 unique reports the item will be treated as Spam and will not be displayed" href="<%=request.getContextPath()%>/Spam?linkId=<%=link.getLinkId()%>&tab=<%=request.getAttribute("tab")%>">Report(<%=link.getSpam()%>)</a>&nbsp;|&nbsp;<%=(link.getTags() != null && link.getTags().trim().length() > 0?link.getTags():"")%></span>
		</div>
		 <div class="col-md-1" style="text-align: center; background-color: <spring:message code='background.color.row' />; color: <spring:message code='vote.color.row' />;"><%=link.getActivists() %><br/><a href="<%=request.getContextPath()%>/AddComment?linkId=<%=link.getLinkId()%>"><%=(link.getActivists()<=1?"comment":"comments")%></a> <% if ( extraLine ){ %><br />&nbsp;<%} %> </div>
</div>

	<% } %>
	<% 
 List <NameCountVO> tags = (List<NameCountVO>) request.getAttribute("tags");
	if (tags != null && tags.size() > 0) {%>
	<div class="row">
	<div class="col-md-12 storystyle" style="background-color: <spring:message code='background.color.row' />; color: <spring:message code='text.color.row'/>;" >	
	<% for (int i =0; tags != null && i < tags.size(); i++  ) {
		NameCountVO tag = tags.get(i);%>
		<%=tag.getCategory() %>(<%=tag.getCount() %>)&nbsp;	
<% 	} %>
	</div>
	</div>
	<%}%>
</table>
       
        
        
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
  </body>
</html>
