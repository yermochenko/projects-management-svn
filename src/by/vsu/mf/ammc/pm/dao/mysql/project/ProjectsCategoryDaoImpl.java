package by.vsu.mf.ammc.pm.dao.mysql.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectsCategoryDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ProjectsCategoryDaoImpl extends BaseDaoImpl implements ProjectsCategoryDao{

	private Map<Integer, ProjectsCategory> cacheMap = new HashMap<>();
	
	public Integer create(ProjectsCategory projects_catogory) throws PersistentException{
		String sql = "INSERT INTO `projects_category` (`name`, `parent_id`) VALUES (?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, projects_catogory.getName().toString());
			if(projects_catogory.getParent() != null)
				statement.setInt(2, projects_catogory.getParent().getId());
			else
				statement.setNull(2, java.sql.Types.NULL);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			
			if(resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new PersistentException();
			}
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	@Override
	public ProjectsCategory read(Integer id) throws PersistentException{
		if ( cacheMap.containsKey( id ) ) {
            return cacheMap.get( id );
        }	
		String sql = "SELECT `name`, `parent_id` FROM `projects_category` WHERE `id` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			ProjectsCategory projects_catogory = null;
			if(resultSet.next()) {
				projects_catogory = new ProjectsCategory();
				projects_catogory.setId(id);				
				ProjectsCategory pc = new ProjectsCategory();
				pc.setId(resultSet.getInt(3));
				projects_catogory.setParent(pc);	
				cacheMap.put( id, projects_catogory );
			}
			return projects_catogory;
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	@Override
	public void update(ProjectsCategory projects_catogory) throws PersistentException{
		String sql = "UPDATE `projects_category` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, projects_catogory.getName().toString());
			statement.setInt(2, projects_catogory.getParent().getId());
			statement.setInt(3, projects_catogory.getId());
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	@Override
	public void delete(Integer id) throws PersistentException {
		String sql = "DELETE FROM `projects_category` WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public List<ProjectsCategory> readAll() throws PersistentException {
		String sql = "SELECT * FROM `projects_category` WHERE `parent_id` IS NULL";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			resultSet = statement.executeQuery();
			
			List<ProjectsCategory> result = new ArrayList<>();
			while(resultSet.next()) {
				ProjectsCategory projects_catogory = new ProjectsCategory();
				projects_catogory.setId(resultSet.getInt("id"));	
				projects_catogory.setName(resultSet.getString("name"));	
				ProjectsCategory pc = new ProjectsCategory();
				pc.setId(resultSet.getInt("parent_id"));
				projects_catogory.setParent(pc);
				result.add(projects_catogory);
			}
			return result;
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
}
