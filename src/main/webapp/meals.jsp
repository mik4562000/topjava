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
    <c:forEach var="meal" items="${meals}">
        <tr ${meal.excess ? 'style="color: red;"' : 'style="color: green;"'}>
            <fmt:parseDate  value="${meal.dateTime}"  pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date" />
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="formattedDateTime" />
            <td>${formattedDateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>