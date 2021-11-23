package view;

/**
 *
 * @author voqua
 * Reference https://www.journaldev.com/1907/java-session-management-servlet-httpsession-url-rewriting
 * 
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Logout", urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");

                        Cookie userName = new Cookie("username", "");
                        userName.setMaxAge(0); 
			response.addCookie(userName);
                        HttpSession session = request.getSession();
                        if(session != null){
                             session.invalidate();
                         }
			response.sendRedirect("Login");                    
    }
       
     @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            log("GET");
            doPost(request, response);
        }
 
}
