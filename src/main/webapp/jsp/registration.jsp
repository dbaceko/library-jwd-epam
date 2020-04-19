<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=${lang}>
<jsp:include page="include/meta.jsp"/>
<body>
<jsp:include page="include/header.jsp"/>

<main class="content">
    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    <form class="form-wrapper" method="post" action="controller"  id="register-form">
        <fieldset class="fieldset">
            <input type="hidden" name="action" value="registerUser">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="registration.legend"/>
            </legend>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.firstname"/></span>
                <input class="input"  type="text" name="firstname"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.firstName} </c:if>>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.lastname"/></span>
                <input type="text" name="lastname" class="input"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.lastName} </c:if>>
            </label>
            <label class="inputfield">
                <span><fmt:message  bundle="${locale}" key="user.passportSN"/></span>
                <input type="password" name="passport" class="input">
            </label>
            <label class="inputfield">
                <span><fmt:message  bundle="${locale}" key="user.login"/></span>
                <input type="text"  name="login" class="input"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.login} </c:if>>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.password"/></span>
                <input type="password"  name="password" class="input">
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.email"/></span>
                <input type="text" name="email" class="input"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.email} </c:if>>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.phone"/></span>
                <input type="text" name="phone" class="input"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.phoneNumber} </c:if>>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="user.address"/></span>
                <input type="text" name="address" class="input"
                <c:if test="${not empty user_registration_data}"> value=${user_registration_data.address} </c:if>>
            </label>
            <div class="terms">
                <label class="check">
                    <input type="checkbox">
                    <span class="checkmark"></span>
                </label>
                <span><fmt:message  bundle="${locale}" key="registration.termsandconditions"/></span>
            </div>
            <label class="inputfield">
                <input class="btn" type="submit" value=<fmt:message  bundle="${locale}" key="registration.btn"/>>
            </label>
        </fieldset>
    </form>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
