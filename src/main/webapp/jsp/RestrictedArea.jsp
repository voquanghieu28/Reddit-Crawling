<%-- 
    Document   : RestrictedArea
    Created on : 24-Oct-2019, 4:30:30 AM
    Author     : voqua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--Reference : https://www.w3schools.com/howto/howto_css_profile_card.asp-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <title>Login Success Page</title>
        <link rel="stylesheet" type="text/css" href="style/style.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <style>
                .card {
                  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
                  max-width: 300px;
                  margin: auto;
                  text-align: center;
                  font-family: arial;
                }

                .title {
                  color: grey;
                  font-size: 18px;
                }

                button {
                  border: none;
                  outline: 0;
                  display: inline-block;
                  padding: 8px;
                  color: white;
                  background-color: #000;
                  text-align: center;
                  cursor: pointer;
                  width: 100%;
                  font-size: 18px;
                }

                a {
                  text-decoration: none;
                  font-size: 22px;
                  color: black;
                }

                button:hover, a:hover {
                  opacity: 0.7;
                }
        </style>
    </head>
    <body class="row-container" align="center">
        
        <br>
            <div class="card">
                <img src="https://cdn0.iconfinder.com/data/icons/user-pictures/100/malecostume-512.png" alt="John" style="width:100%">
                <h1>${username}</h1>
                <p class="title">Tech lead</p>
                
                <div style="margin: 24px 0;">
                </div>
                <p><button>Contact</button></p>
            </div>
        
                
               
               
    </body>
</html>
