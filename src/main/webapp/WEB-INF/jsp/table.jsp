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
    <form action="${requestPath}/delete_copy" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="delete_copy">
    </form>
    <form action="${requestPath}/rename_column" method="post">
        <input type="text" name="oldValue" placeholder="Old value">
        <input type="text" name="newValue" placeholder="New value">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="Rename Column">
    </form>
</div>
<br>
<table>
    <tr>
        <c:forEach items="${table.columns}" var="column">
            <td><c:out value="${column.name}"></c:out></td>
        </c:forEach>
    </tr>
    <c:set var="index" value="-1"></c:set>
    <c:forEach items="${table.rows}" var="row">
        <form action="${requestPath}/${index = index+1}" method="post">
            <tr>
                <c:forEach items="${row.cells}" var="cell">
                    <td><input type="text" name="${cell.column.name}" value="${cell.value}"></td>
                </c:forEach>
                <td>
                    <input type="hidden" name="_method" value="PUT">
                    <input type="submit" value="Edit">
                </td>
            </tr>

        </form>
    </c:forEach>
</table>
</body>
</html>
