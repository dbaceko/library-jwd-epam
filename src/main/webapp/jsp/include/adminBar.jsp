<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>

    <div class="admin-bar">
        <div>
            <ul>
                <li><a class="active" href="${pageContext.request.contextPath}/controller?action=adminPage">
                    <fmt:message bundle="${locale}" key="admin.onlineUserList" />
                </a></li>
                <li><a class="active" href="${pageContext.request.contextPath}/controller?action=allUsersList">
                    <fmt:message bundle="${locale}" key="admin.usersList" />
                </a></li>
                <li><a class="active" href="${pageContext.request.contextPath}/controller?action=addBookPage">
                    <fmt:message bundle="${locale}" key="admin.addBook" />
                </a></li>
                <li><a class="active" href="${pageContext.request.contextPath}/controller?action=addBookComponentPage">
                    <fmt:message bundle="${locale}" key="admin.addBookComponent" />
                </a></li>
                <li><a class="active" href="#">
                    <fmt:message bundle="${locale}" key="admin.orders" />
                </a></li>
            </ul>
        </div>

    </div>
