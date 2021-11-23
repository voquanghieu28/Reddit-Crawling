/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dao.FeedDAO;
import dao.ImageDAO;
import entity.Feed;
import entity.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author voqua
 */
public class ImageLogic extends GenericLogic<Image, ImageDAO> {
    public static final String ID ="id";
    public static final String PATH = "path";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String FEED_ID = "feedId";

    public ImageLogic() {
        super( new ImageDAO() );
    }
    
    @Override
    public List<Image> getAll() {
         return get(()->dao().findAll()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getWithId(int id) {
        return get(()->dao().findById(id)); //To change body of generated methods, choose Tools | Templates.
    }
    

    public List<Image> getImagesWithFeedId(int feedId) {
         return get(()->dao().findByFeedId(feedId)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Image> getImagesNameAndFeed(int feedId, String search) {
         return get(()->dao().findByNameAndFeed(feedId, search)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Image> getImagesWithName(String name) {
         return get(()->dao().findByName(name)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Image getImageWithPath(String path) {
        return get(()->dao().findByPath(path)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Image> getImagesWithDate(Date date) {
         return get(()->dao().findByDate(date)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Image> search(String search) {
         return get(()->dao().findContaining(search)); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "FEED_ID", "NAME", "PATH", "DATE"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, FEED_ID, NAME, PATH, DATE);
    }

    @Override
    public List<?> extractDataAsList(Image e) {
        return Arrays.asList(e.getId(), e.getFeedid().getId(), e.getName(), e.getPath(), e.getDate()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
        Image image = new Image();
        
       
        
        validateString(parameterMap, NAME, 255);
        validateString(parameterMap, PATH, 255);
       
        
        
         if(parameterMap.containsKey(ID)){
            //everything in the map is string so it must first be converted to 
            //appropriate type. have in mind also that values are stored in an
            //array of String, almost always the value is at index zero unless
            //you have used duplicated key/name somewhere.
             validateString(parameterMap, ID, 10);  
            image.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        } 
        
         
        
        
        image.setName(parameterMap.get(NAME)[0]);
        image.setPath(parameterMap.get(PATH)[0]);
        image.setDate(new Date(Long.parseLong(parameterMap.get(DATE)[0])));
        
        return image;
    }
            //https://www.javatpoint.com/java-string-to-date
    
}
