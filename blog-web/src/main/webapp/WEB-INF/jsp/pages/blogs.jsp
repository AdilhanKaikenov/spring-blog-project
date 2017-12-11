<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<table align="center">
    <tr>
        <%--@elvariable id="filter" type="com.epam.adok.core.dao.impl.blog.BlogFilter"--%>
        <form:form method="post" action="${pageContext.request.contextPath}/filter" modelAttribute="filter">
            <td>
                <form:input type="date" path="dateRange.from"/>
            </td>
            <td>
                <form:input type="date" path="dateRange.to"/>
            </td>
            <td>
                    <%--@elvariable id="categoryList" type="java.util.List"--%>
                    <%--<form:checkboxes path="categories" items="${categoryList}" />--%>

                <c:forEach items="${categoryList}" var="category">
                    <%--@elvariable id="category" type="com.epam.adok.core.entity.Category"--%>
                    <%--<br><form:checkbox path="categories" value="${category}"/>${category.genre}--%>
                    <br><input type="checkbox" name="categoryIds" value="${category.id}">${category.genre}
                </c:forEach>
            </td>
            <td>
                <button type="submit">Filter</button>
            </td>
        </form:form>
    </tr>
</table>
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
