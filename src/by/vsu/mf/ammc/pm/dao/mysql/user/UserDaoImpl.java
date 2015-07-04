package by.vsu.mf.ammc.pm.dao.mysql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.apache.log4j.Logger;

public class UserDaoImpl extends BaseDaoImpl implements UserDao{
	private static Logger logger = Logger.getLogger( UserDaoImpl.class );
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
	

	public User readByUserGroup(Integer group_id) throws PersistentException {
		
		if (cacheMap.containsKey(group_id)) {
            return cacheMap.get(group_id);
        }
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlString = "Select id, name, password, first_name, middle_name, last_name, is_admin from user join users_group on users_group.id = user.group_id where group_id = ? AND group_id = parent_id";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, group_id);
			resultSet = statement.executeQuery();
			User user = null;
			if(resultSet.next()){
				user = getEntityFactory().create(User.class);
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setMiddleName(resultSet.getString("middle_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setAdmin(resultSet.getBoolean("is_admin"));
				UsersGroup group = new UsersGroup();
				group.setId(group_id);
				user.setGroup(group);
			}
			
			cacheMap.put(group_id, user);
			
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
	
	@Override
	public List<User> readAll( ) throws PersistentException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> users= new ArrayList<User>(  );
		try {
			String sql = "SELECT * FROM user";
			preparedStatement = getConnection().prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
			resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ){
				User user = getEntityFactory().create( User.class );
				user.setId( resultSet.getInt("id") );
				user.setName( resultSet.getString("name") );
				user.setPassword(resultSet.getString("password"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setMiddleName(resultSet.getString("middle_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setAdmin(resultSet.getBoolean("is_admin"));
				UsersGroup ug = getEntityFactory().create(UsersGroup.class);
				ug.setId(resultSet.getInt("group_id"));
				user.setGroup(ug);
				users.add(user);
			}
			return users;
		}  catch ( SQLException e ) {
			logger.error( "Reading of record was failed. Table 'user'", e );
			throw new PersistentException( e );
		} finally {
			try {
				resultSet.close( );
			} catch ( SQLException | NullPointerException e ) {
			}
			try {
				preparedStatement.close( );
			} catch ( SQLException | NullPointerException e ) {
			}
		}
	}
}
