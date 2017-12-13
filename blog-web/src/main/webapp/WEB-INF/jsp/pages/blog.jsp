<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--@elvariable id="blog" type="com.epam.adok.core.entity.Blog"--%>
<h1>${blog.title}</h1>
<h3>${blog.content}</h3>
<a href="${pageContext.request.contextPath}/blog/${blog.id}/edit">Edit</a> |
<a href="${pageContext.request.contextPath}/blog/${blog.id}/delete">Delete</a> |
<a href="${pageContext.request.contextPath}/blog">Back</a>
<%--@elvariable id="blogComments" type="java.util.List"--%>
<c:if test="${not empty blogComments}">
    <%--@elvariable id="blogComment" type="com.epam.adok.core.entity.comment.BlogComment"--%>
    <c:forEach items="${blogComments}" var="blogComment">
        <div>
            <h4>From : ${blogComment.user.login} | Date : ${blogComment.commentDate}</h4>
        </div>
        <div>
            <p>${blogComment.text}</p>
            <hr>
        </div>
    </c:forEach>
</c:if>
<div>
    <table width="100%">
        <%--@elvariable id="comment" type="com.epam.adok.core.entity.comment.BlogComment"--%>
        <form:form method="post" action="${pageContext.request.contextPath}/blog/comment/submit"
                   modelAttribute="comment">
            <input type="hidden" name="blogId" value="${blog.id}"/>
            <tr>
                <td>
                    <form:textarea path="text"/>
                </td>
            </tr>
            <tr>
                <td>
                    <button type="submit">Submit</button>
                </td>
            </tr>
        </form:form>
    </table>
</div>