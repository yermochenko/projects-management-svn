package by.vsu.mf.ammc.pm.test.user;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.user.ContactDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ContactIdentityMapTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, PersistentException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		
		ContactDaoImpl contactDaoImpl = new ContactDaoImpl();
		contactDaoImpl.setConnection(connection);

		PreparedStatement statemanet = connection.prepareStatement("use pm_db");
		statemanet.executeQuery();
		
		
		
		String sql = " SELECT name, user_id, type_id FROM contact WHERE id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Contact contact = null;
        long timeWithoutCaching = System.currentTimeMillis();
        int i=0;
		int id = 9001;
		while(i<100){
	            preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS);
	            preparedStatement.setInt( 1 , id);
	            resultSet = preparedStatement.executeQuery();
	            contact = new Contact();
	            if( resultSet.next() ) {
	            	contact.setId( id );
	            	contact.setName( resultSet.getString("name") );
	                Integer userId = resultSet.getInt( "user_id" );
	                if( !resultSet.wasNull() ) {
	                    User user = new User();
	                    user.setId( userId );
	                    contact.setUser( user );
	                }
	                Integer typeId = resultSet.getInt( "type_id" );
	                if( !resultSet.wasNull() ) {
	                    ContactsType type = new ContactsType();
	                    type.setId(typeId);
	                    contact.setType(type);
	                }
	            }
	            i++;
		}
		timeWithoutCaching = System.currentTimeMillis() - timeWithoutCaching;
		
		long timeWithCashing = System.currentTimeMillis();
		while(i >0){
			contact = contactDaoImpl.read(id);
			i--;
		}
		timeWithCashing = System.currentTimeMillis() - timeWithCashing;
		System.out.println("time with caching: "+timeWithCashing);
		System.out.println("time without caching: "+timeWithoutCaching);
		System.out.println(timeWithoutCaching - timeWithCashing);
		
		

		
	}

}
