<%-- 
    Document   : CreateFeed
    Created on : 23-Oct-2019, 4:42:35 PM
    Author     : voqua
--%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <h2 align="center">Create Feed</h2>
  <form class="form-horizontal" action="CreateFeed" method="post" style="width: 55%;" >
      
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Host ID:</label>
          <div class="col-sm-10">             
              
              <select class="form-control" name="hostId" id="sel1">                
                    <c:forEach var="host" items="${allHosts}">                      
                       <option>${host.getId()}</option>
                    </c:forEach> 
               </select>
              
              
              <p>${errorMessageHostId}</p>
          </div>
      </div>
          
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Path:</label>
          <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter path" name="path" value="${param.path}">
              <p>${errorMessagePath}</p>
          </div>
      </div>
          
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Type:</label>
          <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter type" name="type" value="${param.type}">
              <p>${errorMessageType}</p>
          </div>
      </div>
          
      <div class="form-group">
          <label class="control-label col-sm-2" for="email">Name:</label>
          <div class="col-sm-10">
              <input type="name" class="form-control" id="name" placeholder="Enter name" name="name" value="${param.name}">
              <p>${errorMessageName}</p>
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