
<%-- 
    Document   : ShowTable-Account-Better
    Created on : 14-Oct-2019, 10:27:43 PM
    Author     : voqua
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}"/></title>
        <!-- https://www.w3schools.com/css/css_table.asp -->
        <!-- Reference: https://www.w3schools.com/bootstrap/bootstrap_forms.asp , https://www.w3schools.com/bootstrap/bootstrap_forms_inputs2.asp-- , https://www.w3schools.com/bootstrap/bootstrap_tables.asp
        -->
        
        <!--link rel="stylesheet" type="text/css" href="style/tablestyle.css"-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        
        <script>
            var isEditActive = false;
            var activeEditID = -1;
            function createTextInput(text, name) {
                var node = document.createElement("input");
                node.name = name;
                node.className = "editor";
                node.className = "w3-input";
               if(name=="id") {
                    node.type="hidden";
               }  else {
                    node.type = "text";
               }
                node.value = text;
                node.size=6;
                return node;
            }
            function convertCellToInput( id, readOnly, name){
                    var idCell = document.getElementById(id);
                    var idInput = createTextInput(idCell.innerText, name);
                    idInput.readOnly = readOnly;
                    idCell.innerText = null;
                    idCell.appendChild(idInput);
                if(name=="id") {
                    var node = document.createElement("p");
                    node.innerHTML=idInput.value;
                    idCell.appendChild(node);
                } 
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
        <style> 
            p { color:red; } p.success{ color:green; }
            input.large {
                width: 18px; 
                height: 18px; 
                align: center;
            }
            
            
            input#maxInput {
                 max-width: 60px;
                 
            }
            

            /* Darker background on mouse-over */
            #del:hover {
              background-color: red;
        </style>
        
    </head>
    <body>
        <!-- https://www.w3schools.com/css/css_table.asp -->
       
        
       <br>
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
 
         
         
        <div class="container" style="max-width: 80%;" id="maxInput" align="center">
                <p>${failedMessage}</p> 
                <p class="success">${successMessage}</p> 
        <form method="post" >
            <table align="center"  class="table table-striped table-hover table-bordered">
                <tr>
                    <th> <button id="del" type="submit" name="delete" value="Delete" class="btn"><i class="fa fa-trash"></i></button></th>
                    <th>Edit</th>
                    <th>ID</th>
                    <th>Display Name</th>
                    <th>User Name</th>
                    <th>Password</th>
                </tr>
                <c:set var="counter" value="-1"/>
                <c:forEach var="entity" items="${entities}">
                    <tr>
                        <td class="delete large">
                            &nbsp &nbsp<input type="checkbox" class="large" name="deleteMark" value="${entity[0]}" />
                        </td>
                        <c:set var="counter" value="${counter+1}"/>
                        <td class="edit" id="${counter}" ><input class="btn btn-default" type="button" name="edit" value="Edit" /></td> <!--<button class="update" name="edit" value="Edit" class="fa fa-edit"></button>-->
                        <c:forEach var="data" items="${entity}">
                            <c:set var="counter" value="${counter+1}"/>
                            <td class="name" id="${counter}" value="${data}">${data}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                <tr>
                    <th><button id="del" type="submit" name="delete" value="Delete" class="btn"><i class="fa fa-trash"></i></button></th>
                    <th>Edit</th>
                    <th>ID</th>
                    <th>Display Name</th>
                    <th>User Name</th>
                    <th>Password</th>
                </tr>
            </table>
        </form>
        </div>
        <div style="text-align: center;">
            <pre>${path}</br>${request}${message}</pre>
        </div>
    </body>
</html>
