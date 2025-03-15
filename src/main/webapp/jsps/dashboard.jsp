<html>
<body>
     <form action="/setExcel" method="post" enctype="multipart/form-data">
     <input type="file" name="file" accept=".xls,.xlsx" required>
     <button type="submit">submit</button>

     <div>
           <h3>${msg}</h3>
     </div>

     <br><br>
     <a href="/getExcel" ><h3>get report in excel format</h3><a>



     </form
     <br>
     <br>
     <a href="/addEmployee"> <h3>add employee</h3></a>
     <br>
     <br>
     <a href="/getEmployee"> <h3>get employee</h3></a>
     <br>

<br>
     <br>
     <a href="/getEmployees"> <h3>get all  employee data </h3></a>
     <br>
          <br>
          <a href="/deleteEmployee"> <h3>delete employee</h3></a><br><br><br>

           <b>
              <a href="/report_tabular/pdf"> Generate report in pdf format </a> <br>
              <a href="/report_tabular/html"> Generate report in html format </a> <br>
              <a href="/report_tabular/csv"> Generate report in csv format </a> <br>
              <a href="/report_tabular/xlsx"> Generate report in xlsx format </a> <br>
              </b>


</body>
</html>