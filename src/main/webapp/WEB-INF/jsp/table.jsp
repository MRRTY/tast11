<%--
  Created by IntelliJ IDEA.
  User: Oleh_Melnychuk
  Date: 11/20/2017
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>
<html>
<head>
    <title>${table.name}</title>
</head>
<body>

<div>
    <form  action="${requestPath}/addRow" method="post">
        <c:forEach items="${table.columns}" var="column">
            <input type="text" placeholder="${column.name}" name="${column.name}">
        </c:forEach>
        <input type="submit" value="Add row">
    </form >
</div>
<br>
<table>
    <tr>
        <c:forEach items="${table.columns}" var="column">
            <td><c:out value="${column.name}"></c:out></td>
        </c:forEach>
    </tr>
    <c:forEach items="${table.rows}" var="row">
        <tr>
            <c:forEach items="${row.cells}" var="cell">
                <td>${cell.value}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>
