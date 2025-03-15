<!DOCTYPE html>
<html>
<head>
    <title>Add Employee</title>
</head>
<body>
    <h2>Add Employee</h2>
    <form action="addingEmployee" method="post">
        <label>ID:</label>
        <input type="text" name="id" required><br><br>

        <label>Name:</label>
        <input type="text" name="name" required><br><br>

        <label>Salary:</label>
        <input type="number" name="salary" required><br><br>

        <label>Designation:</label>
        <input type="text" name="designation" required><br><br>

        <label>Date of Joining:</label>
        <input type="date" name="doj" required><br><br>

        <button type="submit">Submit</button>
    </form>
</body>
</html>
