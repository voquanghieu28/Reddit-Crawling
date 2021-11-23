<%-- 
    Document   : CreateAccount
    Created on : 23-Oct-2019, 7:59:57 PM
    Author     : voqua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- https://www.w3schools.com/bootstrap/tryit.asp?filename=trybs_form_input&stacked=h -->
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
  <h2 align="center">Sign up</h2>
    <form class="form-horizontal" action="CreateAccount" method="post" style="width: 50%;" >
      
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Display name:</label>
          <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter display name" name="firstName" value="${param.firstName}" >
              <p>${errorMessageDisplayName}</p>
          </div>
      </div>
          
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Username:</label>
          <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter username" name="lastName" value="${param.lastName}">
              <p>${errorMessageUsername}</p>
          </div>
      </div>
          
      <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-10">          
        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="joined" value="${param.joined}">
        <p>${errorMessagePassword}</p>
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