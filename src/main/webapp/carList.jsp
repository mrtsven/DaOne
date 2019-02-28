<%@ page import="Models.Car" %>
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
    for (Car car : cars){
        out.print(car);
    }
%>
 <c:forEach var="car" items="${cars}">
     <br> Dit zijn ze dan: <c:out value="${car}"/>
 </c:forEach>
</body>
</html>
