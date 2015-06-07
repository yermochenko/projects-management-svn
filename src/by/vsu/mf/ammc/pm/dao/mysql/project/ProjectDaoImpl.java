package by.vsu.mf.ammc.pm.dao.mysql.project;

import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectDao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;



public class ProjectDaoImpl extends BaseDaoImpl implements ProjectDao{

private Map<Integer, Project> cacheMap = new HashMap<>();
	
	public Integer create(Project project) throws PersistentException{
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try{
			String sqlString = "INSERT INTO project (name, description, category_id, manager_id) VALUES (?,?,?,?)";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setInt(3, project.getCategory().getId());
			statement.setInt(4, project.getManager().getId());
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
	
	public Project read(Integer id) throws PersistentException {
		
		if (cacheMap.containsKey(id)) {
            return cacheMap.get(id);
        }
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlString = "Select name, description, category_id, manager_id from project where id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			Project project = null;
			if(resultSet.next()){
				project = getEntityFactory().create(Project.class);
				project.setId(id);
				project.setName(resultSet.getString("name"));
				project.setDescription(resultSet.getString("description"));
				ProjectsCategory projects_category = new ProjectsCategory();
				projects_category.setId(resultSet.getInt("category_id"));
				project.setCategory(projects_category);
				User user = new User();
				user.setId(resultSet.getInt("manager_id"));
				project.setManager(user);
			}
			
			cacheMap.put(id, project);
			
			return project;
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
	
	public void update(Project project) throws PersistentException {
		PreparedStatement statement = null;
		
		
		
		try{
			String sqlString = "update project set name = ?, description = ?, category_id = ?, manager_id = ? where id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setInt(3, project.getCategory().getId());
			statement.setInt(4, project.getManager().getId());
			statement.setInt(5, project.getId());
			statement.executeUpdate();
			
			cacheMap.put(project.getId(), project);
			
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
			String sqString = "delete from project where id = ?";
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

	@Override
	public boolean projectByUserIsEmpty(Integer user_id) throws SQLException {
		String sql = "SELECT * FROM `project` WHERE `project`.`manager_id` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, user_id);
			resultSet = statement.executeQuery();
			if(resultSet.next()) return false;
				else return true;
		} 
		finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

}