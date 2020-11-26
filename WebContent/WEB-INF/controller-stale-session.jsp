
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="n.css">
  <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
  <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Teko&display=swap" rel="stylesheet">
</head>
<body>
	<header>
		<div id="hd1">
			<div id="hd11">Blog</div>
			<div id="hd12">Share your story!</div>
		</div>
		<div id="hd2">
		<a href="index.do">Login</a>
		<a href="register.do">Register</a>
		</div>
	</header>
	<main>
		
		<div  style="display:flex;flex-direction:column; margin:auto;width:80%;">
		<h2>Controller Stale Session Error</h2>
                
        <p>
            You are attempting to run an action that requires you
            to be logged in.  Perhaps you were logged in, but
            inactive for so long that your session timed out.
            You will need to login again.
        </p>
        <p>
            Click <a href="index.do">here</a> to login.
        </p>
		</div>
	</main>
<footer><p>Copyright @Damir</p></footer>
</body>
</html>