<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" />
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=${lang}>
<head>
    <jsp:include page="include/meta.jsp"/>
</head>
<body>
<jsp:include page="include/header.jsp"/>
<c:if test="${not empty exception_msg}">
    <div class="error-message">
        <p>
            <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
        </p>
    </div>
</c:if>
<main class="content">
    <form class="form-wrapper" method="post" action="controller?post=logIn" id="login-form">
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message key="login.legend"/>
            </legend>
            <label>
                <span><fmt:message key="registration.login"/></span>
                <input type="text" name="login" class="input">
            </label>
            <label>
                <span><fmt:message key="login.password"/></span>
                <input type="password" name="password" class="input">
            </label>
            <div class="terms">
                <label class="check">
                    <input type="checkbox">
                    <span class="checkmark"></span>
                </label>
                <span><fmt:message key="login.remember"/></span>
            </div>
            <label class="inputfield">
                <input class="btn" type="submit" value=<fmt:message key="login.enter"/> >
            </label>

        </fieldset>
    </form>
</main>
<jsp:include page="include/footer.jsp"/>
</html>
