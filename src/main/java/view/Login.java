/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.AccountLogic;

/**
 *
 * @author voqua
 * Reference https://www.journaldev.com/1907/java-session-management-servlet-httpsession-url-rewriting
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
                HttpSession session1 = request.getSession();            
                if (session1.getAttribute("username") != null) {   
                      response.sendRedirect("RestrictedArea");
                      return;
                }
		// GET REQUEST PARAMETER USERNAME NAME AND PASSWORD
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String storedPassword = "";
                
                AccountLogic aLogic = new AccountLogic();
                
                if (aLogic.getAccountWithUser(username)!=null) {
                    storedPassword = aLogic.getAccountWithUser(username).getPassword();
                }
                
                if (username == "") {
                    request.setAttribute("errorMessageUsername", "* You must fill in the name form");                 
                }
                if (password == "") {
                    request.setAttribute("errorMessagePassword", "* You must fill in the password form");                 
                }
                
                //CHECK LOGIN INFO AGAISNT DATABASE INFO
		if(storedPassword.equals(password) && storedPassword!=""){
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("sessionID", session.getId());
			session.setMaxInactiveInterval(60*10);
                        
                        String goBack = request.getParameter("goBack");
                       if(goBack!=null && goBack!="") 
                            response.sendRedirect(goBack);
                       
                        else
                          response.sendRedirect("RestrictedArea");
                       
                       
                       return;
		} else {                  
                        if (username!=null && password!=null &&username!="" &&password!="") {
                            request.setAttribute("errorMessage", "Either username or password or both are in correct!");
                        }
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/Login.jsp");
			rd.include(request, response);
		}
	}
           
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            log("GET");
            doPost(request, response);
        }

}
