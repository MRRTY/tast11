<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>
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
    <a href="${requestPath}/${database.name}"> ${database.name}</a>
    <form action="${requestPath}/${database.name}" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="Delete">
    </form>
</c:forEach>
</body>
</html>
