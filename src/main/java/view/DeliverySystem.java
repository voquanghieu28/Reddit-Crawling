/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.FileInputStream;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author voqua
 */
@WebServlet(name = "DeliverySystem", urlPatterns = {"/image/*"})
public class DeliverySystem extends HttpServlet{
    
    
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
      ServletContext cntx= request.getServletContext();
      

      String filename = System.getProperty("user.home")+"/My Documents/Reddit Images/"+request.getPathInfo().substring(1);
      System.out.println(filename);
      
      String mime = cntx.getMimeType(filename); 
      if (mime == null) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      }

      response.setContentType(mime);
      File file = new File(filename);
      response.setContentLength((int)file.length());

      FileInputStream in = new FileInputStream(file);
      OutputStream out = response.getOutputStream();

      byte[] buf = new byte[1024];
      int count = 0;
      while ((count = in.read(buf)) >= 0) {
         out.write(buf, 0, count);
      }
      
        //out.close();
        //in.close();

    } 
}

