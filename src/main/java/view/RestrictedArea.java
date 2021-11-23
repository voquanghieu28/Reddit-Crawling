package view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author voqua
 */
@WebServlet(name = "RestrictedArea", urlPatterns = {"/RestrictedArea"})
public class RestrictedArea extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        
            //SESSION CHECK
            HttpSession session = request.getSession();
            if (session.getAttribute("username") == null) {
                response.sendRedirect("login.jsp");
            } else {
                request.getRequestDispatcher("/jsp/RestrictedArea.jsp").forward(request, response);
            }
           
    }
    
     @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            log("GET");
            doPost(request, response);
        }
}
