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
    <div class="search-wrapper">
        <aside class="search-form">
            <form class="form-wrapper" method="post" action="controller"  id="book-search-form">
                <fieldset class="fieldset">
                    <input type="hidden" name="action" value="findBook">
                    <legend class="title">
                        <fmt:message  bundle="${locale}" key="book.btn.findBook"/>
                    </legend>
                    <label class="book-select_wrapper">
                        <select class="book-select" name="genre" required>
                            <option selected disabled value=""><fmt:message bundle="${locale}" key="book.genre" /></option>
                            <option value=""><fmt:message bundle="${locale}" key="book.genre.any"/></option>
                            <c:forEach var="genre" items="${genres}">
                                <option value="${genre.uuid}">${genre.genreTitle}</option>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="bookLanguage" required>
                            <option selected disabled value=""><fmt:message bundle="${locale}" key="book.language"/></option>
                            <option value=""><fmt:message bundle="${locale}" key="book.language.any"/></option>
                            <c:forEach var="bookLanguage" items="${bookLanguages}">
                                <option value="${bookLanguage.uuid}">${bookLanguage.languageTitle}</option>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="publisher" required>
                            <option selected disabled value=""><fmt:message bundle="${locale}" key="book.publisher"/></option>
                            <option value=""><fmt:message bundle="${locale}" key="book.publisher.any"/></option>
                            <c:forEach var="publisher" items="${publishers}">
                                <option value="${publisher.uuid}">${publisher.publisherTitle}</option>
                            </c:forEach>
                        </select>
                        <select class="book-select" name="author" required>
                            <option selected disabled value=""><fmt:message bundle="${locale}" key="book.author"/></option>
                            <option value=""><fmt:message bundle="${locale}" key="book.author.any"/></option>
                            <c:forEach var="author" items="${authors}">
                                <option value="${author.uuid}">${author.authorName}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>
                        <span><fmt:message  bundle="${locale}" key="book.title"/></span>
                        <input type="text"  name="bookTitle" class="input"
                                <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.title} </c:if>
                                />
                        <span><fmt:message  bundle="${locale}" key="book.publishYear"/></span>
                        <input type="number"  name="bookPublishYear" class="input"
                                <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.publishYear} </c:if>
                                />
                        <span><fmt:message  bundle="${locale}" key="book.pagesQuantity"/></span>
                        <input type="number"  name="bookPagesQuantity" class="input"
                                <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.pagesQuantity} </c:if>
                                />
                    </label>
                    <label class="inputfield">
                        <input class="btn" type="submit" value=<fmt:message  bundle="${locale}" key="book.btn.findBook"/>/>
                    </label>
                </fieldset>
            </form>
        </aside>
        <c:if test="${not empty bookDTO}">
            <table id="book-list">
                <tr>
                    <th><fmt:message bundle="${locale}" key="book.language"/></th>
                    <th><fmt:message bundle="${locale}" key="book.genre"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publisher"/></th>
                    <th><fmt:message bundle="${locale}" key="book.author"/></th>
                    <th><fmt:message bundle="${locale}" key="book.title"/></th>
                    <th><fmt:message bundle="${locale}" key="book.publishYear"/></th>
                    <th><fmt:message bundle="${locale}" key="book.pagesQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.totalQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.availableQuantity"/></th>
                    <th><fmt:message bundle="${locale}" key="book.description"/></th>
                    <c:if test = "${userRole.equals('ADMIN') || userRole.equals('USER')}">
                        <th><fmt:message bundle="${locale}" key="book.btn.tableHeader.order"/></th>
                    </c:if>
                </tr>
                <c:forEach var="bookDTO" items="${bookDTO}">
                    <tr>
                        <td>${bookDTO.book.bookLanguage.languageTitle}</td>
                        <td>${bookDTO.book.genre.genreTitle}</td>
                        <td>${bookDTO.book.publisher.publisherTitle}</td>
                        <td>${bookDTO.book.author.authorName}</td>
                        <td>${bookDTO.book.title}</td>
                        <td>${bookDTO.book.publishYear}</td>
                        <td>${bookDTO.book.pagesQuantity}</td>
                        <td>${bookDTO.totalBooksQuantity}</td>
                        <td>${bookDTO.totalAvailableBooksQuantity}</td>
                        <td>${bookDTO.book.description}</td>
                        <c:if test = "${userRole.equals('ADMIN') || userRole.equals('USER')}">
                            <td>
                                <form class="book_order-form" action="controller" method="post">
                                    <input type="hidden" name="action" value="addBookOrder">
                                    <input type="hidden" name="bookUUID" value="${bookDTO.book.uuid}">
                                    <c:if test = "${bookDTO.totalAvailableBooksQuantity > 0}">
                                        <fieldset>
                                            <label class="book-select_wrapper">
                                                <button name="orderTypeResult" value="${orderTypeSubscription.name()}">
                                                    <span><fmt:message bundle="${locale}" key="${orderTypeSubscription.localizedName}"/></span>
                                                </button>
                                            </label>
                                        </fieldset>
                                        <fieldset>
                                            <label class="book-select_wrapper">
                                                <button name="orderTypeResult" value="${orderTypeReadingHole.name()}">
                                                    <span><fmt:message bundle="${locale}" key="${orderTypeReadingHole.localizedName}"/></span>
                                                </button>
                                            </label>
                                        </fieldset>
                                    </c:if>
                                    <c:if test = "${bookDTO.totalAvailableBooksQuantity == 0}">
                                        <span><fmt:message bundle="${locale}" key="book.btn.unavailable.order"/></span>
                                    </c:if>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty bookDTO}">
            <p class="error-message">
                <fmt:message bundle="${locale}" key="find.bookDTO.isEmpty"/>
            </p>
        </c:if>
    </div>
</main>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
