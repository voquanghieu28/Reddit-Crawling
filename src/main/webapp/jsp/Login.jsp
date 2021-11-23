<%-- 
    Document   : Login
    Created on : 22-Oct-2019, 2:15:02 AM
    Author     : voqua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <style> p { color:red; } </style>
</head>
<body>

<div class="container" align="center">
  <h2 align="center">Login</h2>
  <form class="form-horizontal" action="Login" style="width: 45%;" method="post">
  
    <div class="form-group ">
      <label class="control-label col-sm-2" for="email" >Username:</label>
      <div class="col-sm-10">
        <input type="name" class="form-control" id="username" placeholder="Enter username" name="username">
        <p>${errorMessageUsername}</p>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-10">          
        <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
        <p>${errorMessagePassword}</p>
      </div>
    </div>
      <p>${errorMessage}</p>
    
    <div class="form-group" >    
      <div>
        <button type="submit" class="btn btn-default">Login</button>
      </div>
    </div>
      
      <input type="hidden" name="goBack" value="${goBack}"/>
      
      <h5>Don't have Web Scraper account yet? <a href="CreateAccount"> Sign up</a></h5>
  </form>
</div>

</body>
</html>
