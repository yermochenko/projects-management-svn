package by.vsu.mf.ammc.pm.dao.mysql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UserDaoImpl extends BaseDaoImpl implements UserDao{
	
	private Map<Integer, User> cacheMap = new HashMap<>();
	
	public Integer create(User user) throws PersistentException{
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try{
			String sqlString = "INSERT INTO user (name, password, first_name, middle_name, last_name, is_admin, group_id) VALUES (?,?,?,?,?,?,?)";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getMiddleName());
			statement.setString(5, user.getLastName());
			statement.setBoolean(6, user.getAdmin());
			statement.setInt(7, user.getGroup().getId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			Integer id = null;
			if(resultSet.next()){
				id = resultSet.getInt(1);
			} else {
				throw new PersistentException();
			}
			return id;
		} catch(SQLException e){
			throw new PersistentException(e);
		} finally {
			try{
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	public User read(Integer id) throws PersistentException {
		
		if (cacheMap.containsKey(id)) {
            return cacheMap.get(id);
        }
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlString = "Select name, password, first_name, middle_name, last_name, is_admin, group_id from user where id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			User user = null;
			if(resultSet.next()){
				user = getEntityFactory().create(User.class);
				user.setId(id);
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setMiddleName(resultSet.getString("middle_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setAdmin(resultSet.getBoolean("is_admin"));
				UsersGroup group = new UsersGroup();
				group.setId(resultSet.getInt("group_id"));
				user.setGroup(group);
			}
			
			cacheMap.put(id, user);
			
			return user;
		} catch (SQLException e){
			throw new PersistentException(e);
		} finally{
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	public void update(User user) throws PersistentException {
		PreparedStatement statement = null;
		
		
		
		try{
			String sqlString = "update user set name = ?, password = ?, first_name = ?, middle_name = ?, last_name = ?, is_admin = ?, group_id = ? where id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getMiddleName());
			statement.setString(5, user.getLastName());
			statement.setBoolean(6, user.getAdmin());
			statement.setInt(7, user.getGroup().getId());
			statement.setInt(8, user.getId());
			statement.executeUpdate();
			
			cacheMap.put(user.getId(), user);
			
		} catch(SQLException e){
			throw new PersistentException();
		} finally {
			try{
				statement.close();
			} catch(SQLException | NullPointerException e){}
		}
	}
	
	public void delete(Integer id) throws PersistentException {
		PreparedStatement statement = null;
		try {
			String sqString = "delete from user where id = ?";
			statement = getConnection().prepareStatement(sqString,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1,id);
			statement.executeUpdate();
			
			cacheMap.remove(id);
		} catch (SQLException e) {
			throw new PersistentException();
		} finally{
			try{
				statement.close();
			} catch(SQLException | NullPointerException e){}			
		}
	}
}
