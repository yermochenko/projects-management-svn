package by.vsu.mf.ammc.pm.test.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ContactsTypeIdentityMapTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, PersistentException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		
		ContactsTypeDaoImpl contactsTypeDaoImpl = new ContactsTypeDaoImpl();
		contactsTypeDaoImpl.setConnection(connection);

		PreparedStatement statemanet = connection.prepareStatement("use pm_db");
		statemanet.executeQuery();
		
		
		
		String sql = " SELECT name,regexp WHERE id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ContactsType contactType = null;
        long timeWithoutCaching = System.currentTimeMillis();
        int i=0;
		int id = 12001;
		while(i<100){
	            preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS);
	            preparedStatement.setInt( 1 , id);
	            resultSet = preparedStatement.executeQuery();
	            contactType = new ContactsType();
	            if( resultSet.next() ) {
	            	contactType.setId( id );
	            	contactType.setName( resultSet.getString("name") );
	            	contactType.setRegexp("regexp");
	            }
	            i++;
		}
		timeWithoutCaching = System.currentTimeMillis() - timeWithoutCaching;
		
		long timeWithCashing = System.currentTimeMillis();
		while(i >0){
			contactType = contactsTypeDaoImpl.read(id);
			i--;
		}
		timeWithCashing = System.currentTimeMillis() - timeWithCashing;
		System.out.println("time with caching: "+timeWithCashing);
		System.out.println("time without caching: "+timeWithoutCaching);
		System.out.println(timeWithoutCaching - timeWithCashing);
		
		

		
	}

}
