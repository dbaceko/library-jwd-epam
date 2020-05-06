<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
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
    <form class="form-wrapper" method="post" action="controller" id="login-form">
        <fieldset class="fieldset">
            <input type="hidden" name="action" value="sendForgetPasswordData">
            <legend class="title">
                <fmt:message bundle="${locale}" key="login.forgetPassword"/>
            </legend>
            <label>
                <span><fmt:message bundle="${locale}" key="user.email"/></span>
                <input type="text" name="email" class="input" required>
            </label>
            <label class="inputfield">
                <input class="btn" type="submit" value=<fmt:message bundle="${locale}" key="login.enter"/> >
            </label>
        </fieldset>
    </form>
</main>
<jsp:include page="include/footer.jsp"/>
</html>
