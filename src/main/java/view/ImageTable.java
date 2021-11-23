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
import entity.Image;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.FeedLogic;
import logic.ImageLogic;
import static logic.ImageLogic.DATE;
import static logic.ImageLogic.FEED_ID;


@WebServlet(name = "ImageTable", urlPatterns = {"/ImageTable"})
public class ImageTable extends HttpServlet {
    private void fillTableData(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        req.setAttribute("entities", extractTableData(req));
        req.setAttribute("request", toStringMap(req.getParameterMap()));
        req.setAttribute("path", path);
        req.setAttribute("title", path.substring(1));
        req.getRequestDispatcher("/jsp/ShowTable-Image.jsp").forward(req, resp);
    }

    private List<?> extractTableData(HttpServletRequest req){
        String search = req.getParameter("searchText");
        //ImageLogic logic = new ImageLogic();
        ImageLogic logic = new ImageLogic();
        req.setAttribute("columnName", logic.getColumnNames());
        req.setAttribute("columnCode", logic.getColumnCodes());
        List<Image> list;
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
        
        //LOGIN CHECK
            HttpSession session = req.getSession();
            if (session.getAttribute("username") == null) {
                req.setAttribute("goBack", "ImageTable");       
                req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);              
                return;
            } 
        
        //CREATE LOGIC
        ImageLogic iLogic = new ImageLogic();
        FeedLogic fLogic = new FeedLogic();
     
        //DELETE
        if(req.getParameterValues("deleteMark")!=null) {
                   
  
           String[] selectedIds = req.getParameterValues("deleteMark");
           
           for(String id: selectedIds) {
               Image image = iLogic.getWithId(Integer.parseInt(id));
               iLogic.delete(image);            
           }
           req.setAttribute("successMessage", "Delete successfully!");
        }
        
        //UPDATE
        if(req.getParameterValues("edit")!=null) {
            Map<String, String[]> parameterMap = req.getParameterMap();  
            
            //Clone a new map because the "parameterMap" cannot modify a key
            Map<String, String[]> new_map = new HashMap<String, String[]>(); 
            new_map.putAll(parameterMap);
                        
            //Conver to Date format from "yyyy-MM-dd" to long string format
            String dateString = req.getParameter(DATE);
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = f.parse(dateString);
                long dateLong = d.getTime();
                new_map.replace(ImageLogic.DATE, new String[]{Long.toString(dateLong)});
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //CHANGE CONVERTED DATE FORMAT IN MAP

             try {    
                Image image = iLogic.createEntity(new_map);
            
                //SET FEED ID
                int feedId = Integer.parseInt(req.getParameter(FEED_ID));   
                image.setFeedid(fLogic.getWithId(feedId));
            
                iLogic.update(image);
                req.setAttribute("successMessage", "Image update successfully!");
            } catch (RuntimeException ex) {
                req.setAttribute("failedMessage", "Image update Failed, please try again!");
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
        return "Smaple of Image Table using JSP";
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


