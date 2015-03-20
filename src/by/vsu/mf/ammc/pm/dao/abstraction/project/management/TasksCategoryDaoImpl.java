package by.vsu.mf.ammc.pm.dao.abstraction.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class TasksCategoryDaoImpl extends BaseDaoImpl implements TasksCategoryDao {

	@Override
	public Integer create(TasksCategory entity) throws PersistentException {
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="INSERT INTO `tasks_category` (`name`, `parent_id`) VALUES (?, ?)";
			ps=getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, entity.getName());
			TasksCategory parent=entity.getParent();
			if (parent!=null && parent.getId()!=null) {
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				//TODO logger.error
				throw new PersistentException();
			}
		}
		catch (SQLException e) {
			throw new PersistentException(e);
		}
		finally {
			try {
				rs.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
			try {
				ps.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
		}
	}

	@Override
	public TasksCategory read(Integer id) throws PersistentException {
		TasksCategory tc=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="SELECT `name`, `parent_id` FROM `tasks_category` WHERE `id` = ?";
			ps=getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if (rs.next()) {
				tc=new TasksCategory();
				tc.setId(id);
				tc.setName(rs.getString("Name"));
				Integer parentId=rs.getInt("parent_id");
				if (!rs.wasNull() && parentId!=id) {
					TasksCategory parent=read(parentId);
					tc.setParent(parent);
				}
			}
			return tc;
		}
		catch (SQLException e) {
			throw new PersistentException(e);
		}
		finally {
			try {
				rs.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
			try {
				ps.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
		}
	}

	@Override
	public void update(TasksCategory entity) throws PersistentException {
		PreparedStatement ps=null;
		try {
			String sql="UPDATE `tasks_category` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
			ps=getConnection().prepareStatement(sql);
			ps.setString(1, entity.getName());
			TasksCategory parent=entity.getParent();
			if (parent!=null && parent.getId()!=null) {
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			ps.setInt(3, entity.getId());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new PersistentException(e);
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
		}
	}

	@Override
	public void delete(Integer id) throws PersistentException {
		PreparedStatement ps=null;
		try {
			String sql="DELETE FROM `tasks_category` WHERE `id` = ?";
			ps=getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new PersistentException(e);
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException|NullPointerException e) {
				
			}
		}
	}

}
