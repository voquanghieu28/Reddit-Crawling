<%-- 
    Document   : ImageViewSearch
    Created on : 26-Oct-2019, 3:02:11 PM
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <title><c:out value="${title}"/></title>
        <!-- https://www.w3schools.com/css/css_table.asp -->
        <link rel="stylesheet" type="text/css" href="style/ImageView.css">
        <script>
            var isEditActive = false;
            var activeEditID = -1;
            function createTextInput(text, name) {
                var node = document.createElement("input");
                node.name = name;
                node.className = "editor";
                node.type = "text";
                node.value = text;
                return node;
            }
            function convertCellToInput( id, readOnly, name){
                var idCell = document.getElementById(id);
                var idInput = createTextInput(idCell.innerText, name);
                idInput.readOnly = readOnly;
                idCell.innerText = null;
                idCell.appendChild(idInput);
            }
            window.onload = function () {
                var elements = document.getElementsByClassName("edit");
                for (let i = 0; i < elements.length; i++) {
                    elements[i].childNodes[0].onclick = function () {
                        var id = elements[i].id;
                        if (isEditActive) {
                            if (activeEditID === id) {
                                this.type = "submit";
                            }
                            return;
                        }
                        isEditActive = true;
                        activeEditID = id;
                        this.value = "Update";
                        
                        <c:forEach var="code" items="${columnCode}">
                            convertCellToInput( ++id, false, "${code}");
                        </c:forEach>
                    };
                }
            };
        </script>
        <style> p { color:red; } </style>
    </head>
    <body>
        <!-- https://www.w3schools.com/css/css_table.asp -->
        <br>
        
        <div class="container" align="center" style="width: 30%; ">
           
        <form >    
            <select class="form-control" name="feedId" id="sel1">   
                <option disabled selected value="">Feed ID</option>
                <c:forEach var="feed" items="${allFeeds}">
                    <option>${feed.getId()}</option>
                </c:forEach> 
            </select>
            <p>${errorMessageFeed}</p>
            
            <br>
            
            <div class="input-group" >
                <input type="text" class="form-control" placeholder="Image Name" name="searchText" >
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
            <p>${errorMessageName}</p>
        </form>
    </div>
        
                  
       <div class="imageContainer">               
                <c:forEach var="entity" items="${entities}">
                    <tr>                                   
                            <img class="imageThumb" src="image/${FileUtility.getFileName(entity[3])}" alt="foo"/>
                    </tr>
                </c:forEach>   
        </div>    
    
        <div style="text-align: center;">
            <pre>${path}</br>${request}${message}</pre>
        </div>
    </body>
</html>
