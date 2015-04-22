package by.vsu.mf.ammc.pm.test.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




import java.sql.Statement;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UserIdentityMapTest {

	public static void main(String[] args) throws PersistentException, SQLException {
		 ConnectionPool connectionPool = ConnectionPool.getInstance();
	        try {
	            connectionPool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000);
	        } catch (PersistentException e) {
	            e.printStackTrace();
	        }
	        System.out.println("User identity map test...\n");	
	        
	        long timeForFirstCircle = System.nanoTime();
	        PreparedStatement statement = null;
	        Connection connection = ConnectionPool.getInstance().getConnection();
	        connection.prepareStatement("use pm_db").executeQuery();
	        String sqlString = "Select name, password, first_name, middle_name, last_name, is_admin, group_id from user where id = ?";
	        for (int i=0; i<10000; i++) {
	            try {
	                statement = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
	                statement.setInt(1, 1);
	                statement.executeQuery();
	            } catch (SQLException e) {
	                System.out.println("Error");
	            }
	        }
	        timeForFirstCircle = System.nanoTime() - timeForFirstCircle;
	        System.out.println("Results of first circle: " + timeForFirstCircle);

	        long timeForSecondCircle = System.nanoTime();
	        UserDaoImpl userDaoImpl = new UserDaoImpl();
	        userDaoImpl.setEntityFactory( new EntityFactory());
	        try {
	        	userDaoImpl.setConnection(connectionPool.getConnection());
	        	userDaoImpl.getConnection().prepareStatement("use pm_db").executeQuery();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        for (int i=0 ; i<10000; i++) {
	            try {
	            	userDaoImpl.read(1);
	            } catch (PersistentException e) {
	                System.out.println("Error");
	            }
	        }
	        timeForSecondCircle = System.nanoTime( ) - timeForSecondCircle;
	        System.out.println("Results of first circle: " + timeForSecondCircle);
	        System.out.println("Difference seconds: ");
	        System.out.println((float)(timeForFirstCircle - timeForSecondCircle)/1000000000);
	   
	}
}
