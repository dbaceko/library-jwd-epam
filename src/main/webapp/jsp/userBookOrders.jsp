<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<main class="content">
    <div class="table_content-wrapper">
        <c:if test="${not empty exception_msg}">
            <div class="error-message">
                <p>
                    <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
                </p>
            </div>
        </c:if>
        <c:if test="${empty exception_msg}">
            <table id="order-list">
                <tr>
                    <th><fmt:message bundle="${locale}" key="order.date.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="order.orderType.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="order.orderStatus.tableHeader"/></th>
                    <th><fmt:message bundle="${locale}" key="book.language"/></th>
                    <th><fmt:message bundle="${locale}" key="book.genre"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publisher"/></th>
                    <th><fmt:message bundle="${locale}" key="book.author"/></th>
                    <th><fmt:message bundle="${locale}" key="book.title"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publishYear"/></th>
                    <th><fmt:message bundle="${locale}" key="book.pagesQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.btn.tableHeader.order"/></th>
                </tr>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td><fmt:formatDate value="${order.date}" pattern="HH:mm:ss dd.MM.yyyy" /></td>
                        <td><fmt:message bundle="${locale}" key="${order.orderType.localizedName}"/></td>
                        <td><fmt:message bundle="${locale}" key="${order.orderStatus.localizedName}"/></td>
                        <td>${order.bookInstance.book.bookLanguage.languageTitle}</td>
                        <td>${order.bookInstance.book.genre.genreTitle}</td>
                        <td>${order.bookInstance.book.publisher.publisherTitle}</td>
                        <td>${order.bookInstance.book.author.authorName}</td>
                        <td>${order.bookInstance.book.title}</td>
                        <td>${order.bookInstance.book.publishYear}</td>
                        <td>${order.bookInstance.book.pagesQuantity}</td>
                        <td>
                            <form class="book_order-form" action="controller" method="post">
                                <input type="hidden" name="orderUUID" value="${order.uuid}">
                                <input type="hidden" name="bookInstanceUUID" value="${order.bookInstance.uuid}">
                                <input type="hidden" name="login" value="${order.user.login}">
                                <c:choose>
                                    <c:when test = "${order.orderStatus.name().equals('ISSUED_BY')}">
                                        <button name="action" value="returnBookOrder">
                                            <span><fmt:message bundle="${locale}" key="order.btn.returnBook"/></span>
                                        </button>
                                    </c:when>
                                    <c:when test = "${order.orderStatus.name().equals('PENDING')}">
                                        <button name="action" value="cancelBookOrder">
                                            <span><fmt:message bundle="${locale}" key="order.btn.cancelOrder"/></span>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <span><fmt:message bundle="${locale}" key="book.btn.unavailable.order"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
