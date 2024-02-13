<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users" method="post">
    <label for="userId">Select User:</label>
    <select name="userId" id="userId">
        <option value="1">User</option>
        <option value="2">Admin</option>
    </select>

    <button type="submit">Login</button>
</form>
</body>
</html>