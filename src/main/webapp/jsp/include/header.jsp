<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<header>
    <ul>
        <li><a href="${pageContext.request.contextPath}/jsp/index.jsp"><h1>
            <fmt:message key="header.home"/>
        </h1></a></li>

        <c:if test="${userRole.equals('GUEST')}">
            <li><a class="active" href="${pageContext.request.contextPath}/jsp/login.jsp">
                <fmt:message key="header.login"/>
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/jsp/registration.jsp">
                <fmt:message key="header.registration"/>
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('ADMIN') || userRole.equals('USER')}">
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=profilePage">
                <fmt:message key="header.profile"/>
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?action=logOut">
                <fmt:message key="header.logout"/>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/controller?action=userOrdersPage">
                <fmt:message key="header.userOrdersPage"/>
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('ADMIN')}">
            <li><a href="${pageContext.request.contextPath}/controller?action=adminPage">
                <fmt:message key="header.adminPage"/>
            </a></li>
        </c:if>
        <li>
            <div class="dropdown">
                <form action="controller" method="post">
                    <input type="hidden" name="action" value="switch-lang">
                    <div class="dropbtn"><fmt:message key="header.lang"/></div>
                    <div class="dropdown-content">
                        <button type="submit" name="language" value="ru">
                            <fmt:message key="header.lang.rus"/>
                        </button >
                        <button type="submit" name="language" value="en">
                            <fmt:message key="header.lang.eng"/>
                        </button>
                    </div>
                </form>
            </div>
        </li>
    </ul>
</header>

