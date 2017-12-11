<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="blog" type="com.epam.adok.core.entity.Blog"--%>
<h1>${blog.title}</h1>
<h3>${blog.content}</h3>
<a href="/edit&id=${blog.id}">Edit</a>
<a href="/delete&id=${blog.id}">Delete</a>

