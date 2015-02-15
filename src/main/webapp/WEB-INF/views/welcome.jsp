<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List,com.sudasuda.app.domain.Link,com.sudasuda.app.domain.User,com.sudasuda.app.domain.FollowUser,com.sudasuda.app.vo.NameCountVO"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="technology buzz">
<meta name="author" content="">
<meta name="robots" content="index, nofollow">
<meta name="google-site-verification" content="lDBIySoUZ9eLc1qy9OMdy7ENVU_OXfIIe07KfDMW8WM">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">

<title>tbuzz.in</title>

<!-- Bootstrap core CSS -->
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
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
 <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]}); 
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Category', 'Submissions'],
          <%User user = (User) request.getSession().getAttribute("userInfo");
          	 List<NameCountVO> counts = (List<NameCountVO>) request.getAttribute("counts");
          	 for (int i = 0; i < counts.size(); i++) {
          		 NameCountVO ccVO = counts.get(i);%>
         	 ['<%=ccVO.getCategory()%>',<%=ccVO.getCount()%>]<%=(i==(counts.size()-1)?"":",")%>
         	 <%}%>
        ]);

        var options = {
          title: "",
          is3D: true,
          backgroundColor: "<spring:message code='background.color.jumbotron' />",
          legendTextStyle: {color: '<spring:message code="googlechart.text.color" />'},	  
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
      }
    </script>    
    
    
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

 <body style="background-color: <spring:message code='background.color' />;">
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=4467235982";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script><table style="width: 1352px; height: 20px">
		<tr>
			<td class="auto-style3" style="width: 1100px"></td>
			<td class="auto-style7">
				<%
					if (request.getSession().getAttribute("userInfo") != null) {
						 user = (User) session.getAttribute("userInfo");
				%>
				<%=user.getUserName()%> <%
 	} else {
 %> &nbsp; <%
 	}
 %>
			</td>
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
					<a class="navbar-brand" style="color: #FF8000" href="#">tbuzz.in</a>
				</div>
			<ul class="nav navbar-nav navbar-right">
						<%
							if (request.getSession().getAttribute("userInfo") == null) {
						%>
						<li><%-- <a style="color: #01B6AD;"
							href="<%=request.getContextPath()%>/SignIn?page=signin">Sign
								In</a>  --%></li>
						<%
							} else {
								 user = (User) request.getSession()
										.getAttribute("userInfo");
						%>
						<li></li>
						<%
							}
						%>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</div>
			
		<div align="center" style="font-size: large; color: #FFFFFF">
			welcome to <b>tbuzz.in</b> <br />&nbsp; <br />  <img
				src="<%=request.getContextPath()%>/resources/img/upspring.jpg"
				height="100" width="100" class="img-rounded" /> 
			<p align="center" style="font-size: medium">
				
				<%--<div id="piechart_3d" style="width: 900px; height: 400px; background-color: <spring:message code='background.color' />;"></div>
				--%>
				<br /> <br/> <br /> <br /> 
				tbuzz.in - technology buzz
				<br>&nbsp;<br /> tbuzz.in is in <b>beta</b>
				<br /> 
				<br />
				   Please share your ideas by mailing
				to <a href="mailto:seydhigal@gmail.com?Subject=Feedback" target="_top">seydhigal@gmail.com</a><br />&nbsp;<br />
				
				<a class="btn btn-lg btn-primary"
					href="<%=request.getContextPath()%>/GetLinks?tab=current"
					role="button">continue to tbuzz.in</a>  
					<br /> <br />
					<a href="https://plus.google.com/111154701683542113744" rel="publisher">Google+</a>
					
				
			</p>
	<br/>&nbsp;
<div class="fb-like" style="" data-href="https://www.facebook.com/pages/%E0%AE%9A%E0%AF%81%E0%AE%9F%E0%AE%9A%E0%AF%81%E0%AE%9Fcom-sudasudacom/609162835843961" data-layout="standard" data-action="like" data-show-faces="true" data-share="true"></div>

		</div>

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
