<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="n.css">
  <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
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
        <jsp:include page="templateheader.jsp"/>
		<div id="m2">
			<div id="m21">
            <form action="index.do" method="POST" id="loginn"> 
				<div id="log">Login to your blog</div>
				<c:forEach var="error" items="${form.formErrors}">
					<h3 style="color:red"> ${error} </h3>
				</c:forEach>
				<c:forEach var="field" items="${form.visibleFields}">
				<div id="tipok">${field.label}</div>
				<input class="inputr" id="${field.name}" type="${field.type}" name="${field.name}" value="${field.value}"/>
				<div style="color:red">
                            ${field.error}
                 </div>
                </c:forEach>
                <button id="btns" type="submit" name="action">Submit</button>
            </form>
				
			</div>
        </div>
	</main>
	<footer><p>Copyright @Damir</p></footer>
</body>
</html>