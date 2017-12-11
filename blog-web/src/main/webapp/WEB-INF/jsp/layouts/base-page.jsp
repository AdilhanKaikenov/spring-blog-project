<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
</head>
<body>
<table width="100%" border="1">
    <tr>
        <td><tiles:insertAttribute name="header"/></td>
    </tr>
    <tr>
        <td><tiles:insertAttribute name="body"/></td>
    </tr>
    <tr>
        <td><tiles:insertAttribute name="footer"/></td>
    </tr>
</table>
</body>
</html>
