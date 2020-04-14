<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${cookie.lang.value}" />
<fmt:setBundle basename="localization" />

<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/ico" href="${pageContext.request.contextPath}/img/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <title><fmt:message key="header.home"/></title>
</head>