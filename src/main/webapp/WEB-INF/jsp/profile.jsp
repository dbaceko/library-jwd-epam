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
    <form class="form-wrapper" method="post" action="controller?post=updateUserInfo" id="user-info-form">
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="profile.legend"/>
            </legend>
            <label>
                <span><fmt:message  bundle="${locale}" key="registration.firstname"/></span>
                <input class="input"  type="text" name="firstname" value=${user_registration_data.firstName}>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="registration.lastname"/></span>
                <input type="text" name="lastname" class="input" value=${user_registration_data.lastName}>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="registration.email"/></span>
                <input type="text" name="email" class="input" value=${user_registration_data.email}>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="registration.phone"/></span>
                <input type="text" name="phone" class="input" value=${user_registration_data.phoneNumber}>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="registration.address"/></span>
                <input type="text" name="address" class="input" value=${user_registration_data.address}>
            </label>
            <label class="inputfield">
                <input class="btn" type="submit" value=<fmt:message  bundle="${locale}" key="profile.btn.updateInfo"/>>
            </label>
        </fieldset>
    </form>
    <div class="books_in_usage">
        lib content
    </div>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
