<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="admin-bar">
    <div>
        <ul>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=adminPage">
                <fmt:message bundle="${locale}" key="admin.onlineUserList" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=allUsersList&recordsPerPage=10&currentPage=1">
                <fmt:message bundle="${locale}" key="admin.usersList" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=addBookPage">
                <fmt:message bundle="${locale}" key="admin.addBook" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=addBookComponentPage">
                <fmt:message bundle="${locale}" key="admin.addBookComponent" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=openOrdersPage&recordsPerPage=10&currentPage=1">
                <fmt:message bundle="${locale}" key="admin.orders" />
            </a></li>
        </ul>
    </div>
</div>
