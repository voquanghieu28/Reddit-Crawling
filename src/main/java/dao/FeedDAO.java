/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Feed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author voqua
 */
public class FeedDAO extends GenericDAO<Feed> {
    public FeedDAO () {
        super(Feed.class);
    }
    
    public List<Feed> findAll() {
        return findResults("Feed.findAll", null);
    }
    
    
    public Feed findById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findResult( "Feed.findById", map);
    }
    
    public List<Feed> findByHostId(int hostId) {
        Map<String, Object> map = new HashMap<>();          
        map.put("hostId", hostId);
        return findResults("Feed.findByHostId", map);
    }
    
    public Feed findByPath (String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        return findResult("Feed.findByPath", map);   
    }
    
    public List<Feed> findByType (String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        return findResults("Feed.findByType", map);   
    }
    
    public List<Feed> findByName (String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return findResults("Feed.findByPath", map);
    }
    
    public List<Feed> findContaining (String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("search", search);
        return findResults("Feed.findContaining", map);
    }
    
    
}
