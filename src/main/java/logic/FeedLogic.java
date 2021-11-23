/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dao.FeedDAO;
import dao.HostDAO;


import entity.Feed;
import entity.Host;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 *
 * @author voqua
 */
public class FeedLogic extends GenericLogic<Feed, FeedDAO>{
    public static final String ID ="id";
    public static final String PATH = "path";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String HOST_ID = "hostId";
    
    public FeedLogic() {
        super( new FeedDAO() );
    }
    
    @Override
    public List<Feed> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Feed getWithId( int id){
        return get(()->dao().findById(id));
    }
    
    public List<Feed> getFeedsWithHostID(int hostId) {
        return get(()->dao().findByHostId(hostId));
    }

    public Feed getFeedWithPath(String path) {
        return get(()->dao().findByPath(path));
    }
    
    public List<Feed> getFeedsWithType(String type) {
        return get(()->dao().findByType(type));
    }
    
    public List<Feed> getFeedsWithName(String name) {
        return get(()->dao().findByName(name));
    }
    
    public List<Feed> search(String search) {
        return get(()->dao().findContaining(search));
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "HOST_ID", "PATH", "TYPE", "NAME"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getColumnCodes() {
       return Arrays.asList(ID, HOST_ID, PATH, TYPE, NAME); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<?> extractDataAsList(Feed e) {
       return Arrays.asList(e.getId(), e.getHostid().getId(), e.getPath(), e.getType(), e.getName()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Feed createEntity(Map<String, String[]> parameterMap) {
         Feed feed = new Feed();
         
         validateString(parameterMap, HOST_ID, 10);
         validateString(parameterMap, PATH, 255);
         validateString(parameterMap, TYPE, 45);
         validateString(parameterMap, NAME, 45);
         if(parameterMap.containsKey(ID)){
            //everything in the map is string so it must first be converted to 
            //appropriate type. have in mind also that values are stored in an
            //array of String, almost always the value is at index zero unless
            //you have used duplicated key/name somewhere.
            validateString(parameterMap, ID, 10);
            feed.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
         
         
         feed.setName(parameterMap.get(NAME)[0]);
         feed.setPath(parameterMap.get(PATH)[0]);
         feed.setType(parameterMap.get(TYPE)[0]);
         
        return feed;
        
    }
    
    
}
