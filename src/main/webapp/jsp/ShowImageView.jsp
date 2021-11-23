<%-- 
    Document   : ShowImageView
    Created on : 16-Oct-2019, 10:53:32 AM
    Author     : voqua
--%>
<%@page import="common.FileUtility"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="entity.Image"%>
<%@page import="logic.ImageLogic"%>



<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <!-- Reference from: https://www.w3schools.com/bootstrap/bootstrap_carousel.asp -->
</head>
<body>
<br>
<div class="container">
   
   
        <div class="container" align="center" style="width: 40%; ">
        <form >
            <div class="input-group" >
                <input type="text" class="form-control" placeholder="Search" name="searchText" >
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
        </form>
    </div>
   <br>
    
    
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
      
    
       <!-- This is for the css's class value because the first img is "item active" and the rest are "item"-->
        <c:set var="local" value="item active" />
        <c:forEach var="entity" items="${entities}">
            
            <div class="<c:out value="${local}"></c:out> "> 
                
                
                        <img class="imageThumb" src="image/${FileUtility.getFileName(entity[3])}" alt="foo" style=" width:auto; height:560px; margin-left: auto; margin-right: auto;  display: block;"/>
                       
                         
                        
            </div>
            <c:set var="local" value="item" />
                    
        </c:forEach>   
        
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
        
    
</div>

</body>
</html>