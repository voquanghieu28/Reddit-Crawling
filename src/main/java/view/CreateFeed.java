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



import entity.Feed;
import entity.Host;
import logic.HostLogic;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.FeedLogic;
import org.codehaus.plexus.util.StringUtils;


//@WebServlet(name = "CreateHostBasic", urlPatterns = {"/CreateHostBasic"})
@WebServlet(name = "CreateFeed", urlPatterns = {"/CreateFeed"})
public class CreateFeed extends HttpServlet {

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
        
        
         //SESSION CHECK
            HttpSession session = request.getSession();
            if (session.getAttribute("username") == null) {
                request.setAttribute("goBack", "CreateFeed");       
                request.getRequestDispatcher("/jsp/Login.jsp").forward(request, response);              
                return;
            } 
        
        
        
        
        
        //create logic
        FeedLogic fLogic = new FeedLogic();
        HostLogic hLogic = new HostLogic();
        request.setAttribute("allHosts", hLogic.getAll());     
        
        //grab the parameter NAME first
        String name = request.getParameter(FeedLogic.NAME);
        String hostId = request.getParameter(FeedLogic.HOST_ID);
        String path = request.getParameter(FeedLogic.PATH);
        String type = request.getParameter(FeedLogic.TYPE);
        //since it is unique we check for duplicates.
        
   
        Boolean addSuccess = true;
        
        // VALIDATION 
        if (hostId == "") {
            request.setAttribute("errorMessageHostId", "* You must fill in the Host ID form!"); 
            addSuccess = false;
        } else if (!StringUtils.isNumeric(hostId) && hostId!=null) {
            request.setAttribute("errorMessageHostId", "* Host value must be number!");
            addSuccess = false;
        } 
        else if (StringUtils.isNumeric(hostId)) {
          try { 
            if (hLogic.getWithId(Integer.parseInt(hostId)) == null) {
                request.setAttribute("errorMessageHostId", "* This Host value not exists!");
                addSuccess = false;
            }
          } catch (NumberFormatException ex) {
              request.setAttribute("errorMessageHostId", "* In valid value, should be less than 2,147,483,647!");
              addSuccess = false;
          }    
        } 
        //NAME
        if (name == "") {
            request.setAttribute("errorMessageName", "* You must fill in the name form!"); 
            addSuccess = false;
        } 
        //PATH
        if (path == "") {
            request.setAttribute("errorMessagePath", "* You must fill in the path form"); 
            addSuccess = false;
        } else {
            
            if (fLogic.getFeedWithPath(path)!=null) {
            request.setAttribute("errorMessagePath", "* This path already exists! Please choose another one!");
                addSuccess = false;              
            }
        }
        
        //TYPE
        if (type == "") {
            request.setAttribute("errorMessageType", "* You must fill in the type form"); 
            addSuccess = false;
        } 
        
        if(addSuccess && name !=null) {
            try {
                Host host = hLogic.getWithId(Integer.parseInt(hostId));
                Feed feed = fLogic.createEntity(request.getParameterMap());
                feed.setHostid(host);
                fLogic.add(feed);
                errorMessage = "";
                request.setAttribute("successMessage", "Feed add successfully!"); 
        
            } catch (RuntimeException ex) {
                errorMessage = ex.getMessage();
                request.setAttribute("errorMessageName", errorMessage); 

            }
        }
                
                
                
        if (request.getParameter("view") != null && addSuccess) {
                    //if view button is pressed redirect to the appropriate table
                    response.sendRedirect("FeedTable");
        } 
        else {
            request.getRequestDispatcher("/jsp/CreateFeed.jsp").forward(request, response);
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