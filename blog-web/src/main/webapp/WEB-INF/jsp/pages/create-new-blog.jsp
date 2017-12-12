<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border="1px" cellpadding="30%" align="center">
    <h2 align="center">Create New Blog</h2>
    <%--@elvariable id="newBlog" type="com.epam.adok.core.entity.Blog"--%>
    <form:form method="post" action="${pageContext.request.contextPath}/create" modelAttribute="newBlog">
        <tr>
            <td>Title :</td>
            <td><form:input path="title"/></td>
        </tr>
        <tr>
            <td>Content :</td>
            <td><form:textarea path="content"/></td>
        </tr>
        <tr>
            <td>Categories :</td>
            <td>
                <c:forEach items="${categoryList}" begin="0" end="${categoryList.size()}" var="category"/>

                    <%--@elvariable id="categoryList" type="java.util.List"--%>
                <c:forEach items="${categoryList}" var="category">
                    <%--@elvariable id="category" type="com.epam.adok.core.entity.Category"--%>
                    <%--<br><form:checkbox path="categories" value="${category}"/>${category.genre}--%>
                    <br><input type="checkbox" name="categoryIds" value="${category.id}">${category.genre}
                </c:forEach>
            </td>
        </tr>
        <td colspan="2" align="center">
            <button type="submit">Create New Blog</button>
        </td>
    </form:form>
</table>
