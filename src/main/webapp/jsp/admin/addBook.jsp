<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="localization" var="locale"/>
<fmt:setBundle basename="exceptionMessages" var="exc_msg"/>

<!DOCTYPE html>
<html lang=${lang}>
<jsp:include page="../include/meta.jsp"/>
<body>
<jsp:include page="../include/header.jsp"/>

<main class="content">
    <jsp:include page="../include/adminBar.jsp"/>
    <c:if test="${not empty exception_msg}">
        <div class="error-message">
            <p>
                <fmt:message bundle="${exc_msg}"  key="${exception_msg}"/>
            </p>
        </div>
    </c:if>
    <form class="form-wrapper" method="post" action="controller"  id="book-add-form">
        <fieldset class="fieldset">
            <input type="hidden" name="action" value="addBook">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="admin.addBook"/>
            </legend>
            <label class="book-select_wrapper">
                <select class="book-select" name="genre" required>
                    <option selected disabled><fmt:message bundle="${locale}" key="book.genre" /></option>
                    <c:forEach var="genre" items="${genres}">
                        <option value="${genre.uuid}">${genre.genreTitle}</option>
                    </c:forEach>
                </select>
                <select class="book-select" name="bookLanguage" required>
                    <option selected disabled><fmt:message bundle="${locale}" key="book.language"/></option>
                    <c:forEach var="bookLanguage" items="${bookLanguages}">
                        <option value="${bookLanguage.uuid}">${bookLanguage.languageTitle}</option>
                    </c:forEach>
                </select>
            </label>
            <label class="book-select_wrapper">
                <select class="book-select" name="publisher" required>
                    <option selected disabled><fmt:message bundle="${locale}" key="book.publisher"/></option>
                    <c:forEach var="publisher" items="${publishers}">
                        <option value="${publisher.uuid}">${publisher.publisherTitle}</option>
                    </c:forEach>
                </select>
                <select class="book-select" name="author" required>
                    <option selected disabled><fmt:message bundle="${locale}" key="book.author"/></option>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.uuid}">${author.authorName}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.title"/></span>
                <input type="text"  name="bookTitle" class="input" required
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.title} </c:if>
                />
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.publishYear"/></span>
                <input type="number"  name="bookPublishYear" class="input" required
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.publishYear} </c:if>
                />
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.pagesQuantity"/></span>
                <input type="number"  name="bookPagesQuantity" class="input" required
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.pagesQuantity} </c:if>
                />
            </label>
            <label>
                <span><fmt:message  bundle="${locale}" key="book.quantity"/></span>
                <input type="number"  name="bookQuantity" class="input" required
                    <c:if test="${not empty bookPreviousData}"> value=${bookPreviousData.availableBookQuantity} </c:if>
                />
            </label>
            <label>
                <textarea name="bookDescription" rows="10" cols="45" class="input" maxlength="500" required
                          placeholder="<fmt:message  bundle="${locale}" key="book.description"/>">
                    <c:if test="${not empty bookPreviousData}"> ${bookPreviousData.description} </c:if>
                </textarea>
            </label>
            <label class="inputfield">
                <input class="btn" type="submit" value=<fmt:message  bundle="${locale}" key="book.btn.addBook"/>/>
            </label>
        </fieldset>
    </form>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
