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
    <title>${database.name}</title>
</head>
<body>


<br>
<div>
    <form  action="${requestPath}/table" method="post">
        <input type="hidden" name="dbName" value="${database.name}">
        <input type="text" name="tableName" placeholder="Table name">
        <input type="text" name="command" placeholder="Command line">
        <input type="submit" value="Create table">
    </form >
</div>

<c:forEach items="${database.tables}" var="table">
    <a href="/home/${database.name}/${table.name}"> ${table.name}</a>
</c:forEach>
<br>
</body>
</html>
