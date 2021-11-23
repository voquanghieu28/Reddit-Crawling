<%-- 
    Document   : CreateHost
    Created on : 20-Oct-2019, 10:52:57 PM
    Author     : voqua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Reference: https://www.w3schools.com/bootstrap/bootstrap_forms.asp , https://www.w3schools.com/bootstrap/bootstrap_forms_inputs2.asp-->
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <style> p { color:red; } p.success{ color:green; }</style>
</head>
<body>

<div class="container" align="center">
  <h2 align="center">Create Host</h2>
  
    <form class="form-horizontal" action="CreateHost" method="post" style="width: 50%; ">
          <div class="form-group">
            <label class="control-label col-sm-2" for="email">Name:</label>
            <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter name" name="name" value="${param.name}">
              <p>${errorMessage}</p>
              <p class="success">${successMessage}</p>
            </div>
          </div>


          <div class="form-group" algin="center">        
              <button type="submit" class="btn btn-default" name="add" >Add</button>
              <button type="submit" class="btn btn-default" name="view">Add and view</button>
          </div>
  </form>
    
</div>

</body>
</html>

