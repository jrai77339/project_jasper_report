<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Details</title>
</head>
<body>
    <h1>Employee Dashboard</h1>

    <!-- Display employee details if present -->
    <div id="employeeInfo" style="display: ${employee != null ? 'block' : 'none'};">
        <h2>Employee Details</h2>
        <p><strong>ID:</strong> ${employee.id}</p>
        <p><strong>Name:</strong> ${employee.name}</p>
        <p><strong>Salary:</strong> ${employee.salary}</p>
        <p><strong>Designation:</strong> ${employee.designation}</p>
        <p><strong>Date of Joining:</strong> ${employee.doj}</p>
    </div>

    <!-- Display error message if employee not found -->
    <div id="error" style="color: red; display: ${error != null ? 'block' : 'none'};">
        <p>${error}</p>
    </div>
    <a href="/">dashboard</a>
</body>
</html>
