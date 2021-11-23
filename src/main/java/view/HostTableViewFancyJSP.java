package view;

import entity.Host;
import logic.HostLogic;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shariar
 */
@WebServlet(name = "HostViewFancyJSPBetter", urlPatterns = {"/HostTable","/HostViewFancyJSPBetter"})
public class HostTableViewFancyJSP extends HttpServlet {

     private void fillTableData(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        req.setAttribute("entities", extractTableData(req));
        req.setAttribute("request", toStringMap(req.getParameterMap()));
        req.setAttribute("path", path);
        req.setAttribute("title", path.substring(1));
        req.getRequestDispatcher("/jsp/ShowTable-Host-Better.jsp").forward(req, resp);
    }

    private List<?> extractTableData(HttpServletRequest req){
        String search = req.getParameter("searchText");
        HostLogic logic = new HostLogic();
        req.setAttribute("columnName", logic.getColumnNames());
        req.setAttribute("columnCode", logic.getColumnCodes());
        List<Host> list;
        if (search != null) {
            list = logic.search(search);
        } else {
            list = logic.getAll();
        }
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return appendDatatoNewList(list, logic::extractDataAsList);
    }

    private <T> List<?> appendDatatoNewList(List<T> list, Function<T, List<?>> toArray) {
        List<List<?>> newlist = new ArrayList<>(list.size());
        list.forEach(i -> newlist.add(toArray.apply(i)));
        return newlist;
    }

    private String toStringMap(Map<String, String[]> m) {
        StringBuilder builder = new StringBuilder();
        m.keySet().forEach((k) -> {
            builder.append("Key=").append(k)
                    .append(", ")
                    .append("Value/s=").append(Arrays.toString(m.get(k)))
                    .append(System.lineSeparator());
        });
        return builder.toString();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        log("POST");
         //SESSION CHECK
            HttpSession session = req.getSession();
            if (session.getAttribute("username") == null) {
                req.setAttribute("goBack", "HostViewFancyJSPBetter");       
                req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);              
                return;
            } 
        
        HostLogic hLogic = new HostLogic();
        
     
        //DELETE
        if(req.getParameterValues("deleteMark")!=null&& req.getParameterValues("edit")==null) {

           String[] selectedIds = req.getParameterValues("deleteMark");
           
           for(String id: selectedIds) {
               Host host = hLogic.getWithId(Integer.parseInt(id));
               hLogic.delete(host);            
           }
           req.setAttribute("successMessage", "Delete successfully!");
        }
        
        //UPDATE
        if(req.getParameterValues("edit")!=null && req.getParameterValues("deleteMark")==null) {
            Map<String, String[]> parameterMap = req.getParameterMap();      
            try {    
                Host host = hLogic.createEntity(parameterMap);
                hLogic.update(host);
                req.setAttribute("successMessage", "Host update successfully!");
            } catch (RuntimeException ex) {
                req.setAttribute("failedMessage", "Host update Failed, please try again!");
            }
        } 
              
       fillTableData(req, resp);
       
       
     
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("GET");
        doPost(req, resp);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("PUT");
        doPost(req, resp);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log("DELETE");
        doPost(req, resp);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Smaple of Host Table using JSP";
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
