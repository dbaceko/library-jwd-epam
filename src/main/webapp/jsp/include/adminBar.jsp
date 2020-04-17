<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <div class="admin-bar">
        <ul>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=logInPage">
                <fmt:message key="admin.onlineUserList" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=registerPage">
                <fmt:message key="admin.usersList" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=registerPage">
                <fmt:message key="admin.addBook" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=registerPage">
                <fmt:message key="admin.orders" />
            </a></li>
        </ul>
    </div>
