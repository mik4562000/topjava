<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Add new / edit meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Add new / edit meal</h2>
  <form method="POST" action='meals' name="frmAddMeal">
    DateTime : <input
          type="datetime-local" name="dateTime"
<%--          <fmt:parseDate  value="${meal.dateTime}"  pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date" />--%>
          <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm" var="formattedDateTime" />
          value="<c:out value="${formattedDateTime}" />" /> <br />

    Description : <input
          type="text" name="description"
          value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
          type="text" name="calories"
          value="<c:out value="${meal.calories}" />" /> <br />

      <input type="submit" value="Submit" />
      <button onclick="window.history.back()" type="button">Cancel</button>
  </form>
</body>
</html>
