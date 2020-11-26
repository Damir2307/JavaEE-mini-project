<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div id="m1">
	<c:forEach var="users" items="${users}">
			<form class="" method="POST" action="visit.do">
                    	<input type="hidden" name="UName" value="${ users.userName }" />
                    	<input type="hidden" name="UFName" value="${ users.firstName }" />
                    	<input type="hidden" name="ULName" value="${ users.lastName }" />
                    	<button id="btnadm" type="submit">${users.firstName} ${users.lastName}</button>
              </form>
	</c:forEach>
	</div>
</body>
</html>