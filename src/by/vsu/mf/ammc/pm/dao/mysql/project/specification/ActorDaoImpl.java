package by.vsu.mf.ammc.pm.dao.mysql.project.specification;

import by.vsu.mf.ammc.pm.dao.abstraction.project.specification.ActorDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.specification.Actor;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActorDaoImpl extends BaseDaoImpl implements ActorDao {

	@Override
	public Integer create(Actor entity) throws PersistentException{
		
		Statement statement = null;
		int resultSet;	
		
		try
		{		
		statement = getConnection().createStatement();
		String sql = "INSERT INTO actor (`id`, `name`, `project_id`) "
				 + "VALUES (" + Integer.toString(entity.getId()) + 
				 ", '" + entity.getName() + "', " + 
				 Integer.toString(entity.getProject().getId()) + ")";
		resultSet = statement.executeUpdate(sql);
		return resultSet;
		}
		catch(SQLException e)
		{
			throw new PersistentException(e);	
		}
		finally
		{
			try {
                statement.close();
			} 
			catch (SQLException | NullPointerException e) {}			
		}
	}

	@Override
	public Actor read(Integer id) throws PersistentException {

		Statement statement = null;
		ResultSet resultSet = null;
		
		try
		{		
		statement = getConnection().createStatement();
		resultSet = statement.executeQuery(
				 "SELECT * FROM `actor` WHERE id = "
				 + Integer.toString(id) );
		Actor actor = null;
		if(resultSet.next())
		{
			actor = new Actor();
			actor.setId(id);
			actor.setName(resultSet.getString("name"));
			Project pr = new Project();
			pr.setId(resultSet.getInt("project_id"));
			actor.setProject(pr);
		}
		return actor;
		}
		catch(SQLException e)
		{
			throw new PersistentException(e);
		}
		finally{
			try {
                resultSet.close();
			} 
			catch (SQLException | NullPointerException e) {}
			try {
                statement.close();
			} 
			catch (SQLException | NullPointerException e) {}		
		}
	}

	@Override
	public void update(Actor entity) throws PersistentException {
		
		Statement statement = null;
		
		try
		{
		
		statement = getConnection().createStatement();
		statement.executeUpdate(
				 "UPDATE `actor` SET name = '"
				 + entity.getName() + "', project_id = " + 
				 Integer.toString(entity.getProject().getId())
				 + " WHERE id = " + entity.getId() );	
		
		}
		catch(SQLException e)
		{
			throw new PersistentException(e);
		}
		finally{
			try {
                statement.close();
			} 
			catch (SQLException | NullPointerException e) {}		
		}

	}

	@Override
	public void delete(Integer id) throws PersistentException {

		Statement statement = null;
		
		try
		{		
		statement = getConnection().createStatement();
		statement.executeUpdate(
				 "DELETE FROM `actor` WHERE id = " + 
				 Integer.toString(id));
		}
		catch(SQLException e)
		{
			throw new PersistentException(e);
		}
		finally{
			try {
                statement.close();
			} 
			catch (SQLException | NullPointerException e) {}		
		}

	}

}
