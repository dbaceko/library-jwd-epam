<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>
<!DOCTYPE html>
<html lang=${lang}>
<head>
    <jsp:include page="include/meta.jsp"/>
</head>
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
        <a href="${pageContext.request.contextPath}/controller?action=bookCatalogPage">
           temp link
        </a>
    </main>
    <jsp:include page="include/footer.jsp"/>
</body>
</html>
