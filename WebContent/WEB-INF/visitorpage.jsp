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
		<a href="home.do">Home</a>
			<form action="logout.do" method="POST">
			<button type="submit" id="logoutt">Log Out</button>
			</form>
		</div>
	</header>
	<main>
	
		<jsp:include page="templateheader.jsp"/>
		<div id="m2">
			<p id="hp"><c:out value="${UFName}" />'s Blog</p>
			<c:forEach var="field" items="${form.visibleFields}">
			<p>${field.error}</p>
			</c:forEach>
			<c:forEach var="error" items="${form.formErrors}">
				<p class="error"> ${error} </p>
		    </c:forEach>
			<c:forEach var="post" items="${posts}">
				<div id="post">
				<div id="hm21">
				<c:set var = "UName" value="${UName}"/>
					<p id="adam">${UFName} ${ULName}</p>
					<p id="data">Posted on ${ post.date }</p>
				</div>
				<p id="posted">${ post.content }</p>
			</div>
			<c:set var="userName" value="${sessionScope.userUN}"/>
			<c:forEach var="comment" items="${comments}">
			<c:if test="${comment.postId == post.id}">
			<div id="comment">
				<div id="cpost">
					<div id="chm21">
					<c:if test="${comment.userName eq userName}">
					<form class="delete-form" method="POST" action="commentdelete.do">
						<input type="hidden" name="UName" value="${UName}" />
						<input type="hidden" name="UFName" value="${UFName}" />
						<input type="hidden" name="ULName" value="${ULName}" />
                    	<input type="hidden" name="id" value="${ comment.id }" />
                    	<button id="x2" type="submit">X</button>
                	</form>
                	</c:if>
						<p id="cadam">${comment.userName}</p>
						<p id="cdata">Commented on ${ comment.date }</p>
					</div>
					
					<p id="cposted">${ comment.content }</p>
				</div>
			</div> 
			</c:if>
			</c:forEach>
			
		    <form action="comment.do" method="post">
				<input type="hidden" name="UName" value="${UName}" />
				<input type="hidden" name="UFName" value="${UFName}" />
				<input type="hidden" name="ULName" value="${ULName}" />
					<c:forEach var="field" items="${form.visibleFields}">
						<div id="vnpost">
							<p>Comment: </p>
							<input type="${field.type}" name="${field.name}" value="${field.value}" autofocus/>
							<input type="hidden" name="id" value="${post.id}" />
							<p>${field.error}</p>
						</div>
						<button id="npostsub">Comment</button>
					</c:forEach>
			</form>
				</c:forEach>			
		</div>
	</main>
<footer><p>Copyright @Damir</p></footer>
</body>
</html>