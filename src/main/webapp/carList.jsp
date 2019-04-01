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
    request.setAttribute("cars", cars);
//    for (Car car : cars){
//        out.print(car);
//    }
%>
 Dit zijn ze dan:
 <c:forEach items="${cars}" var="car" >
     <c:out value="${car.carName}"/>
 </c:forEach>

 <ui:repeat value="#{cars}" var="item">
     <h:outputText id="item" value="#{item.carName}" />
 </ui:repeat>
</body>
</html>
