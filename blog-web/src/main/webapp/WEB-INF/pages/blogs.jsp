<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Blogs</title>
</head>
<body>
<table border="1" cellpadding="2" align="center">
    <tr bgcolor="#8fbc8f">
        <th>
            ID
        </th>
        <th>
            Title
        </th>
        <th>
            Categories
        </th>
        <th>

        </th>
    </tr>
    <%--@elvariable id="blogs" type="java.util.List"--%>
    <c:forEach items="${blogs}" var="blog">
        <%--@elvariable id="blog" type="com.epam.adok.core.entity.Blog"--%>
        <tr>
            <td>
                    ${blog.id}
            </td>
            <td>
                    ${blog.title}
            </td>
            <td>
                <c:forEach items="${blog.categories}" var="category">
                    <%--@elvariable id="category" type="com.epam.adok.core.entity.Category"--%>
                <li>${category.genre}
                    </c:forEach>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/blog/${blog.id}">Read</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
