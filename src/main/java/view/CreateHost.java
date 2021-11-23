package view;

import entity.Host;
import logic.HostLogic;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shariar
 */
@WebServlet(name = "CreateHostBasic", urlPatterns = {"/CreateHost"})
public class CreateHost extends HttpServlet {
   
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
        //processRequest(request, response);
       // request.getRequestDispatcher("/jsp/CreateHost.jsp").forward(request, response);
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
                request.setAttribute("goBack", "CreateHost");       
                request.getRequestDispatcher("/jsp/Login.jsp").forward(request, response);              
                return;
            } 
        
        //create logic
        HostLogic hLogic = new HostLogic();
     
        
        //grab the parameter NAME first
        String name = request.getParameter(HostLogic.NAME);
        //since it is unique we check for duplicates.
       
        Boolean addSuccess = false;
        
        // VALIDATION 
                if (name == "") {
                    request.setAttribute("errorMessage", "*You must fill in the name form");
                    
                }
                else if (hLogic.getHostWithName(name) == null && name !=null) {
                    //if no duplicates create the entity and add it.   
                    try {

                        Host host = hLogic.createEntity(request.getParameterMap());
                        hLogic.add(host);
                        errorMessage = "";
                        request.setAttribute("successMessage", "Host add successfully!"); 
                        addSuccess=true;
                    } catch (RuntimeException ex) {
                        errorMessage = ex.getMessage();
                        request.setAttribute("errorMessage", ex.getMessage()) ;
                        
                    }
                } else if (name !=null) {                   
                   request.setAttribute("errorMessage", "*Host name: " + name + " already exsists!");                   
                }
                
                
                if (request.getParameter("view") != null && addSuccess) {
                            //if view button is pressed redirect to the appropriate table
                            response.sendRedirect("HostTable");
                } 
                else {
                    request.getRequestDispatcher("/jsp/CreateHost.jsp").forward(request, response);
                }
                
                addSuccess = false;
     
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Create a Host Entity";
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
