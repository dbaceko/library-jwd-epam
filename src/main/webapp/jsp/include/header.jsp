<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="localization" />

<header>
    <ul>
        <li><a href="${pageContext.request.contextPath}/controller?get=indexPage"><h1>
            <fmt:message key="header.home"/>
        </h1></a></li>
        <c:if test="${empty userRole}">
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=logInPage">
                <fmt:message key="header.login" />
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=registerPage">
                <fmt:message key="header.registration" />
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('ADMIN') || userRole.equals('USER')}">
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=profilePage">
                <fmt:message key="header.profile"/>, ${login}
            </a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=logOut">
                <fmt:message key="header.logout" />
            </a></li>
        </c:if>
        <c:if test="${userRole.equals('ADMIN')}">
            <li><a class="active" href="${pageContext.request.contextPath}/controller?get=adminPage">
                <fmt:message key="header.adminPage"/>
            </a></li>
        </c:if>

        <li>
            <div class="dropdown">
                <form action="controller" method="post">
                    <input type="hidden" name="post" value="switch-lang">
                    <div class="dropbtn"><fmt:message key="header.lang"/></div>
                    <div class="dropdown-content">
                        <button type="submit" name="language" value="ru">
                            <fmt:message key="header.lang.rus"/>
                        </button >
                        <button type="submit" name="language" value="en">
                            <fmt:message key="header.lang.eng"/>
                        </button >
                    </div>
                </form>
            </div>
        </li>
    </ul>
</header>

