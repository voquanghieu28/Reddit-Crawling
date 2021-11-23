/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dao.AccountDAO;
import entity.Account;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author voqua
 */
public class AccountLogic extends GenericLogic<Account, AccountDAO>{

    public static final String DISPLAY_NAME ="ABCXYZ";
    public static final String PASSWORD = "ABCXYZ";
    public static final String USER = "ABCXYZ";
    public static final String ID="ABCXYZ";
    
    public AccountLogic() {
        super( new AccountDAO() );
    }
    
    @Override
    public List<Account> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Account getWithId( int id){
        return get(()->dao().findById(id));
    }
    
    public Account getAccountWithDisplayName( String displayName){
        return get(()->dao().findByDisplayName(displayName));
    }
    
    public Account getAccountWithUser( String user ){
        return get(()->dao().findByUser(user));
    }
    
    public List<Account> getAccountsWithPassword( String password ){
        return get(()->dao().findByPassword(password));
    }
    
    public Account getAccountWith( String username, String password ){
        return get(()->dao().validateUser(username, password));
    }
    
    public List<Account> search( String search ){
        return get(()->dao().findContaining(search));
    }
    
    @Override
    public List<String> getColumnNames() {
         return Arrays.asList("ID", "DISPLAY_NAME", "USER", "PASSWORD");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, DISPLAY_NAME, USER, PASSWORD); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<?> extractDataAsList(Account e) {
        return Arrays.asList(e.getId(), e.getDisplayName(), e.getUser(), e.getPassword()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account createEntity(Map<String, String[]> parameterMap) {
        //never create another logic within another logic.
        //create a new Entity object
        Account account = new Account();
        
        validateString(parameterMap, DISPLAY_NAME, 45);
        validateString(parameterMap, USER, 45);
        validateString(parameterMap, PASSWORD, 45);
        //ID is generated so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        if(parameterMap.containsKey(ID)){
            //everything in the map is string so it must first be converted to 
            //appropriate type. have in mind also that values are stored in an
            //array of String, almost always the value is at index zero unless
            //you have used duplicated key/name somewhere.
            validateString(parameterMap, ID, 10);
            account.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        //have in mind also that values are stored in an
        //array of String, almost always the value is at index zero unless
        //you have used duplicated key/name somewhere.
        account.setDisplayName(parameterMap.get(DISPLAY_NAME)[0]);
        account.setUser(parameterMap.get(USER)[0]);
        account.setPassword(parameterMap.get(PASSWORD)[0]);
        
        
        
        return account;
    }

   
    
}
