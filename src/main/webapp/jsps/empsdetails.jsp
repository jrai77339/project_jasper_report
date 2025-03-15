<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee List</title>
</head>
<body>

    <h1>Employee List</h1>

    <!-- Display error message if no employees are found -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <!-- Display employee table if employees exist -->
    <c:if test="${not empty employees}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Salary</th>
                    <th>Designation</th>
                    <th>Date of Joining</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iterate through the list of employees -->
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td>${employee.id}</td>
                        <td>${employee.name}</td>
                        <td>${employee.salary}</td>
                        <td>${employee.designation}</td>
                        <td>${employee.doj}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>
