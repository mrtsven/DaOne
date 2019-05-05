<%@ page import="Models.Car.Car" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Sven
  Date: 11-2-2019
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car list</title>
</head>
<body>
<h1>Bunch of cars</h1>
<%
List<Car> cars = (List<Car>) request.getAttribute("cars");
    request.setAttribute("cars", cars);
%>

<table border="1" width="90%">
    <tr><th>Id</th><th>Name</th>
    <c:forEach items="${list}" var="u">
        <tr><td>${u.getId()}</td><td>${u.getCarName()}</td>
    </c:forEach>
</table>

</body>
</html>

