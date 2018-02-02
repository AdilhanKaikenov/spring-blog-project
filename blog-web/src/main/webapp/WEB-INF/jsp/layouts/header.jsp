<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize access="isAnonymous()">
    <a href="${pageContext.request.contextPath}/login">Login</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</sec:authorize>

