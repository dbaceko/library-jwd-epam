<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang=${cookie.lang.value}>
<head>
    <jsp:include page="/jsp/include/meta.jsp"/>
</head>
<body>
    <c:redirect url="/controller?get=indexPage"/>
</body>
</html>
