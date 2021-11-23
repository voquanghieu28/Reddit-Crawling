/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author voqua
 */
import entity.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.AccountLogic;


@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

    private String errorMessage = null;
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * get method is called first when requesting a URL. since this servlet
     * will create a host this method simple delivers the html code. 
     * creation will be done in doPost method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("GET");
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * this method will handle the creation of entity. as it is called by user
     * submitting data through browser.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("POST");
        
        
        //create logic
        AccountLogic aLogic = new AccountLogic();
        
     
        
        //grab the parameter NAME first
        String displayName = request.getParameter(AccountLogic.DISPLAY_NAME);
        String username = request.getParameter(AccountLogic.USER);
        String password = request.getParameter(AccountLogic.PASSWORD);
        //since it is unique we check for duplicates.
        
   
        Boolean addSuccess = true;
        
        // VALIDATION 
        
        //NAME
        if (displayName == "") {
            request.setAttribute("errorMessageDisplayName", "* You must fill in the Host ID form"); 
            addSuccess = false;
        } else {
            
            if (aLogic.getAccountWithDisplayName(displayName)!=null) {
                request.setAttribute("errorMessageDisplayName", "* This name already exists! Please choose another one!");
                addSuccess = false;
                
            }
        }
        
        //USERNAME
        if (username == "") {
            request.setAttribute("errorMessageUsername", "* You must fill in the username form"); 
            addSuccess = false;
        } else {
            
            if (aLogic.getAccountWithUser(username)!=null) {
                request.setAttribute("errorMessageUsername", "* This Username already exists! Please choose another one!");
                addSuccess = false;
                
            }
        }
        //PASSWORD
        if (password == "") {
            request.setAttribute("errorMessagePassword", "* You must fill in the password form"); 
            addSuccess = false;
        }
       
        
        if(addSuccess && displayName !=null) {
            try {
                Account account = aLogic.createEntity(request.getParameterMap());              
                aLogic.add(account);
                errorMessage = "";
                request.setAttribute("successMessage", "Account add successfully");
        
            } catch (RuntimeException ex) { 
                errorMessage = ex.getMessage();
                request.setAttribute("errorMessagePassword", ex.getMessage()) ;

            }
        }
        
                
                
        if (request.getParameter("view") != null && addSuccess) {
                    //if view button is pressed redirect to the appropriate table
                    response.sendRedirect("AccountTable");
        } 
        else {
            request.getRequestDispatcher("/jsp/CreateAccount.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Create a Feed Entity";
    }

    private static final boolean DEBUG = true;

    public void log( String msg) {
        if(DEBUG){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
            getServletContext().log( message);
        }
    }

    public void log( String msg, Throwable t) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
        getServletContext().log( message, t);
    }
}
