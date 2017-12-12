<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="blog" type="com.epam.adok.core.entity.Blog"--%>
<h1>${blog.title}</h1>
<h3>${blog.content}</h3>
<a href="${pageContext.request.contextPath}/blog/${blog.id}/edit">Edit</a>
<a href="${pageContext.request.contextPath}/blog/${blog.id}/delete">Delete</a>

