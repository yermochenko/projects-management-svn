package by.vsu.mf.ammc.pm.test.junit.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UserDaoImplTest {
			
	@Test
    public void testCRUD( ) throws PersistentException
    {
		//Class.forName("com.mysql.jdbc.Driver");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserDaoImpl userDaoImpl = new UserDaoImpl(); 
		userDaoImpl.setConnection(connection);
		userDaoImpl.setEntityFactory(new EntityFactory());
		
		User user = userDaoImpl.getEntityFactory().create(User.class);		
		user.setName("test");
		user.setPassword("mypass");
		user.setFirstName("Petya");
		user.setMiddleName("Petrovich");
		user.setLastName("Petrov");
		user.setAdmin(false);
		UsersGroup group = new UsersGroup(); 
		group.setId(13011);
		user.setGroup(group);
		
		try {
			connection.prepareStatement("use pm_db").executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//create
		Integer identity = 0;
		try {
			identity = userDaoImpl.create(user);
		} catch (PersistentException e) {
			Assert.assertTrue( false );
		}
		
		user.setId(identity);
		
		//read		
		User userRead = null;
		try {
			userRead = userDaoImpl.read(identity);
		} catch (PersistentException e2) {
			Assert.assertTrue( false );
		}
		
		Assert.assertEquals(user.getId(), userRead.getId());
		Assert.assertEquals(user.getFirstName(), userRead.getFirstName());
		Assert.assertEquals(user.getLastName(), userRead.getLastName());
		Assert.assertEquals(user.getMiddleName(), userRead.getMiddleName());
		Assert.assertEquals(user.getName(), userRead.getName());
		Assert.assertEquals(user.getPassword(), userRead.getPassword());
		Assert.assertEquals(user.getAdmin(), userRead.getAdmin());
		Assert.assertEquals(user.getContacts(), userRead.getContacts());
		Assert.assertEquals(user.getGroup().getId(), userRead.getGroup().getId());
		
		//update
		try {
			user.setFirstName("Petr");
			userDaoImpl.update(user);
			userRead = userDaoImpl.read(identity);			
		} catch (PersistentException e1) {
			Assert.assertTrue( false );
		}
		
		Assert.assertEquals(user.getFirstName(), userRead.getFirstName());
		
		//delete
		try {
			userDaoImpl.delete(identity);
			userRead = userDaoImpl.read(identity);
		} catch (PersistentException e) {
			Assert.assertTrue( false );
		}
		
		Assert.assertNull(userRead);
		
	}
    
}
