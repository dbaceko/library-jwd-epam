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
    <form class="form-wrapper" method="post" action="controller">
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message bundle="${locale}" key="book.btn.addBook.genre"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="genre">
                <button name="action" value="addBookGenre">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>

        </fieldset>
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.language"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="bookLanguage">
                <button name="action" value="addBookLanguage">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>

        </fieldset>
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.publisher"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="publisher">
                <button name="action" value="addBookPublisher">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>

        </fieldset>
        <fieldset class="fieldset">
            <legend class="title">
                <fmt:message  bundle="${locale}" key="book.btn.addBook.author"/>
            </legend>
            <label class="book-select_wrapper">
                <input type="text" name="author">
                <button name="action" value="addBookAuthor">
                    <span><fmt:message bundle="${locale}" key="admin.addBookComponent"/></span>
                </button>
            </label>
        </fieldset>
    </form>
</main>
<jsp:include page="../include/footer.jsp"/>
</body>
</html>
