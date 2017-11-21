<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>

</head>
<body>
<div>
    <form  action="/home/database" method="post">
        <input type="text" name="name" placeholder="Database name">
        <input type="submit" value="Create database">
    </form >
</div>
<br>
<c:forEach items="${databaseList}" var="database">
    <a href="/home/${database.name}"> ${database.name}</a>
</c:forEach>
</body>
</html>
