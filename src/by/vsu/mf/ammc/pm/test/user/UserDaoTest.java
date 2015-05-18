package by.vsu.mf.ammc.pm.test.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UserDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		UserDaoImpl userDaoImpl = new UserDaoImpl(); 
		userDaoImpl.setConnection(connection);
		userDaoImpl.setEntityFactory(new EntityFactory());
		User user = userDaoImpl.getEntityFactory().create(User.class);
		user.setName("test user");
		user.setPassword("mypass");
		user.setFirstName("Petya");
		user.setMiddleName("Petrovich");
		user.setLastName("Petrov");
		user.setAdmin(false);
		UsersGroup group = new UsersGroup(); 
		group.setId(14040);
		user.setGroup(group);
		
		PreparedStatement statement = connection.prepareStatement("use pm_db");
		System.out.println("test method create!___________");
		Integer identity = userDaoImpl.create(user);
		statement = connection.prepareStatement("Select * from user");
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			System.out.print(resultSet.getInt("id") + "_" + resultSet.getString("name") + "_" + resultSet.getString("password") + "_" 
					+ resultSet.getString("first_name") + "_" +	resultSet.getString("middle_name") + "_" +	resultSet.getString("last_name") + "_" 
					+ resultSet.getBoolean("is_admin") + "_" + resultSet.getInt("group_id"));
			System.out.println();
		} 
		
		System.out.println("test mathod read!_______");
		user = userDaoImpl.read(identity);
		if(user != null) {
			System.out.println(user.getId() + " " + user.getName() + " " + user.getPassword() + " " + user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName() + " " + user.getAdmin() + " " + user.getGroup());
		} else {
			System.out.println("method read is broken");
		}
		
		System.out.println("test method update!____");
		userDaoImpl.update(user);
		user = userDaoImpl.read(identity);
		if(user != null){
			System.out.println(user.getId() + " " + user.getName() + " " + user.getPassword() + " " + user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName() + " " + user.getAdmin() + " " + user.getGroup());
		} else {
			System.out.println("method update is broken");
		}
		
		System.out.println("test method delete!_____");
		userDaoImpl.delete(identity);
		statement = connection.prepareStatement("Select * from user");
		resultSet = statement.executeQuery();
		System.out.println();
		while(resultSet.next()) {
			
			System.out.print(resultSet.getInt("id") + "_" + resultSet.getString("name") + "_" + resultSet.getString("password") + "_" 
					+ resultSet.getString("first_name") + "_" +	resultSet.getString("middle_name") + "_" +	resultSet.getString("last_name") + "_" 
					+ resultSet.getBoolean("is_admin") + "_" + resultSet.getInt("group_id"));
			System.out.println();
		}
	}
}
