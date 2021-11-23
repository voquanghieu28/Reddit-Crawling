/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import common.FileUtility;
import static common.FileUtility.createDirectory;
import entity.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.FeedLogic;
import logic.ImageLogic;
import scraper.Post;
import scraper.Scraper;
import scraper.Sort;


/**
 *
 * @author voqua
 */
@WebServlet(name = "ImageViewSearch", urlPatterns = {"/ImageViewSearch"})
public class ImageViewSearch extends HttpServlet {
    
    private void fillTableData(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        req.setAttribute("entities", extractTableData(req)); 
        req.setAttribute("request", toStringMap(req.getParameterMap()));
        req.setAttribute("path", path);
        req.setAttribute("title", path.substring(1));

        req.getRequestDispatcher("/jsp/ImageViewSearch.jsp").forward(req, resp);
    }


    private List<?> extractTableData(HttpServletRequest req){
        String search = req.getParameter("searchText");
        String feedId = req.getParameter("feedId");
        ImageLogic logic = new ImageLogic();
        req.setAttribute("columnName", logic.getColumnNames());
        req.setAttribute("columnCode", logic.getColumnCodes());
        List<Image> list;
        if (search != null && search != "" && feedId!="" && feedId!=null) {
            System.out.println(search);
            //FIND IMAGE WITH FEED ID AND NAME
            list = logic.getImagesNameAndFeed(Integer.parseInt(feedId), search);
            
        } else {
            list = logic.getAll();
        }
        if (list == null || list.isEmpty()) {
            req.setAttribute("errorMessageName", "No result founded!"); 
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
                req.setAttribute("goBack", "ImageViewSearch");       
                req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);              
                return;
            } 
        fillTableData(req, resp); 
    } 
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("GET");
        
        //SESSION CHECK
            HttpSession session = request.getSession();
            if (session.getAttribute("username") == null) {
                request.setAttribute("goBack", "ImageViewSearch");       
                request.getRequestDispatcher("/jsp/Login.jsp").forward(request, response);              
                return;
            } 
        
        String name = request.getParameter("searchText");
        String feed = request.getParameter("feedId");
        
        //NAME
        if (name == "") {
            request.setAttribute("errorMessageName", "* You must fill in the name form!"); 
        } if (feed==null && name!=null  ) {
            request.setAttribute("errorMessageFeed", "* You must pick a feed!"); 
        } 
        

        
        //GET ALL FEEDS IN ORDER TO SET DROP DOWN LIST
        FeedLogic fLogic = new FeedLogic();
        request.setAttribute("allFeeds", fLogic.getAll());  
        
        createDirectory(System.getProperty("user.home")+"/My Documents/Reddit Images/");
        
        //CREATE LOGIC
        ImageLogic iLogic = new ImageLogic();
        FeedLogic fLoic = new FeedLogic();
            
           Consumer<Post> saveImage = (Post post) -> {
                //if post is an image and SFW AND THE URL NOT EXSISTS IN THE DB
                if (post.isImage() && !post.isOver18() && iLogic.getImageWithPath(post.getUrl())==null) {
                    String path = post.getUrl();
                    
                    FileUtility.downloadAndSaveFile(path, System.getProperty("user.home")+"/My Documents/Reddit Images/");  

                        Map<String, String[]> map = new HashMap<>();
                        map.put(ImageLogic.NAME, new String[]{post.getTitle()});
                        map.put(ImageLogic.PATH, new String[]{post.getUrl()});
                        map.put(ImageLogic.DATE, new String[]{Long.toString(post.getDate().getTime())});
                         
                       Image local = iLogic.createEntity(map);
                       local.setFeedId( fLoic.getWithId(4) ); 
                                             
                     iLogic.add(local);	                     
                }
            };

        //CREATE A NEW SCRAPPER
        Scraper scrap = new Scraper();
        scrap.authenticate().buildRedditPagesConfig("Wallpaper", 20, Sort.HOT);  
        scrap.requestNextPage().proccessNextPage(saveImage);  
 
        doPost(request, response);

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

