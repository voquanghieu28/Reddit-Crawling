package logic;

import dao.HostDAO;
import entity.Host;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Shariar
 */
public class HostLogic extends GenericLogic<Host, HostDAO>{
    
    /**
     * create static final variables with proper name of each column.
     * this way you will never manually type it again, instead always
     * refer to these variables.
     * 
     * by using the same name as column id and html element names we can
     * make our code simpler. this is not recommended for proper production
     * project.
     */
    public static final String ID = "id";
    public static final String NAME = "name";

    public HostLogic() {
        super( new HostDAO() );
    }
    
    @Override
    public List<Host> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Host getWithId( int id){
        return get(()->dao().findById(id));
    }
    
    public Host getHostWithName( String name){
        return get(()->dao().findByName(name));
    }
    
    @Override
    public List<Host> search( String search){
        return get(()->dao().findContaining(search));
    }

    @Override
    public Host createEntity(Map<String, String[]> parameterMap) {
        
        Objects.requireNonNull(parameterMap);
        //never create another logic within another logic.
        //create a new Entity object
        Host host = new Host();
        validateString(parameterMap, NAME, 45);
        
        //ID is generated so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        if(parameterMap.containsKey(ID)){
            //everything in the map is string so it must first be converted to 
            //appropriate type. have in mind also that values are stored in an
            //array of String, almost always the value is at index zero unless
            //you have used duplicated key/name somewhere.
            validateString(parameterMap, ID, 10);
            host.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        //have in mind also that values are stored in an
        //array of String, almost always the value is at index zero unless
        //you have used duplicated key/name somewhere.
        
        /*
        if(parameterMap.containsKey(ID) && parameterMap.get(NAME)[0]!=null ) {
            String name = parameterMap.get(NAME)[0];
            if(name.isEmpty() || name.length()>45) {
                throw new RuntimeException("Name must be between 1 and 45 char's");
            }   
        } else {
            throw new RuntimeException("Name must exsists!");
        }*/
        //validateString(parameterMap, NAME, name->name.isEmpty() || name.length()>45);
        
        host.setName(parameterMap.get(NAME)[0]);
        
        return host;
    } 
    
    /**
     * this method is used to send a list of all names to be used form table
     * column headers. by having all names in one location there is less chance of mistakes.
     * 
     * this list must be in the same order as getColumnCodes and extractDataAsList
     * 
     * @return list of all column display names.
     */
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "Name");
    }
    
    /**
     * this method returns a list of column names that match the official column
     * names in the db. by having all names in one location there is less chance of mistakes.
     * 
     * this list must be in the same order as getColumnNames and extractDataAsList
     * 
     * @return list of all column names in DB.
     */
    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, NAME);
    }

    /**
     * return the list of values of all columns (variables) in given entity.
     * 
     * this list must be in the same order as getColumnNames and getColumnCodes
     * 
     * @param e - given Entity to extract data from.
     * @return list of extracted values
     */
    @Override
    public List<?> extractDataAsList( Host e) {
        return Arrays.asList(e.getId(), e.getName());
    }
}
