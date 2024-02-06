<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" cellpadding="8">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <c:forEach var="item" items="${meals}">
        <tr ${item.isExcess() ? 'style="color: red;"' : ''}>
            <fmt:parseDate  value="${item.getDateTime()}"  pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date" />
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="formattedDateTime" />
            <td>${formattedDateTime}</td>
            <td>${item.getDescription()}</td>
            <td>${item.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>