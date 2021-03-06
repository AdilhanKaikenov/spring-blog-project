<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border="1px" cellpadding="30%" align="center">
    <h2 align="center">Edit Blog</h2>
    <%--@elvariable id="blogModelEdit" type="com.epam.adok.web.model.blog.BlogCreateModel"--%>
    <form:form method="post" action="${pageContext.request.contextPath}/blog/edit" modelAttribute="blogModelEdit">
        <form:hidden path="id"/>
        <tr>
            <td>Title :</td>
            <td><form:input path="title"/></td>
            <%--<td><form:errors path="title"/></td>--%>
        </tr>
        <tr>
            <td>Content :</td>
            <td><form:textarea path="content"/></td>
            <td><form:errors path="content"/></td>
        </tr>
        <tr>
            <td>Categories :</td>
            <td>
                    <%--@elvariable id="categoryList" type="java.util.List"--%>
                <c:forEach items="${categoryList}" var="category">
                    <%--@elvariable id="category" type="com.epam.adok.core.entity.Category"--%>
                    <br><form:checkbox path="categoriesIds" value="${category.id}"/>${category.genre}
                    <%--<br><input type="checkbox" name="categoryIds" value="${category.id}">${category.genre}--%>
                </c:forEach>
            </td>
            <td><form:errors path="categoriesIds"/></td>
        </tr>
        <td colspan="2" align="center">
            <button type="submit">Update Blog</button> |
            <a href="${pageContext.request.contextPath}/blog/${blogModelEdit.id}">Back</a>
        </td>
    </form:form>
</table>
