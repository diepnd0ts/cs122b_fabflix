<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<style type="text/css">
	#header{
		height: 80px;
		width: 100%;
		background: #c40000;
	}

	#header h1{
		margin: 0px;
		float: left;
		text-shadow: 4px 4px black;
	}
	label{
		color: white;
	}	
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <TITLE>FabFlix</TITLE>
  <script src='https://www.google.com/recaptcha/api.js'></script>

</HEAD>

<BODY style="background: black">
<div id="header">
	<h1 style="padding-top: 20px; color: white; margin-left:20px">FabFlix</h1>
</div>
<div>
<br>
<H1 style="color: white"ALIGN="CENTER">Welcome to FabFlix!</H1>
<center>
	<br>
	<%
	String logout = (String)request.getAttribute("logout");
	if (logout != null)
		session.invalidate(); 
	String error = (String)request.getAttribute("error");
	if (error != null) {%>
		<p style="color: red"><%=error%></p>
	<%} else {
	%>
	<p style="color: white">Login To Begin Shopping!</p>
	<% } %>
	<br>
<form style="height:300px; width: 300px;" action="/Fabflix/servlet/TomcatForm" method="post"  class="form-horizontal">

 <div class="form-group">
  <label for="usr">Email:</label>
  <input type="text" class="form-control" id="usr" name="email">
</div>
<div class="form-group">
  <label for="pwd">Password:</label>
  <input type="password" class="form-control" id="pwd" name="password">
  <input type="hidden" value="<%=logout%>" name="logout">
</div> 
  <br>
  <div class="g-recaptcha" data-sitekey="6Lf2_BUUAAAAABd8AgC-Eb-9u-NUDW9xsCPgCTQ4"></div>
  <br>
  <div class="form-group">
      <input type="submit" value="Login">
  </div>
</form>
</center>
</div>
</BODY>
</HTML>
