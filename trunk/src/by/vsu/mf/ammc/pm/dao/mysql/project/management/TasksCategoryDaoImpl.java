package by.vsu.mf.ammc.pm.dao.mysql.project.management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TasksCategoryDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TasksCategoryDaoImpl extends BaseDaoImpl implements TasksCategoryDao {
	private Map<Integer, TasksCategory> identityMap = new HashMap<Integer, TasksCategory>();

	@Override
	public Integer create(TasksCategory category) throws PersistentException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO `tasks_category` (`name`, `parent_id`) VALUES (?, ?)";
		try {
			preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, category.getName());
			TasksCategory parent = category.getParent();
			if(parent != null && parent.getId() != null) {
				preparedStatement.setInt(2, parent.getId());
			} else {
				preparedStatement.setNull(2, Types.INTEGER);
			}
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
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
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {}
			identityMap.clear();
		}
	}

	@Override
	public TasksCategory read(Integer id) throws PersistentException {
		TasksCategory category = identityMap.get(id);
		if(category == null) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String sql = "SELECT `name`, `parent_id` FROM `tasks_category` WHERE `id` = ?";
			try {
				preparedStatement = getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					category = getEntityFactory().create(TasksCategory.class);
					category.setId(id);
					category.setName(resultSet.getString("name"));
					Integer parentId = resultSet.getInt("parent_id");
					if(!resultSet.wasNull()) {
						TasksCategory parent = getEntityFactory().create(TasksCategory.class);
						parent.setId(parentId);
						category.setParent(parent);
					}
					identityMap.put(id, category);
				}
			} catch(SQLException e) {
				throw new PersistentException(e);
			} finally {
				try {
					resultSet.close();
				} catch(SQLException | NullPointerException e) {}
				try {
					preparedStatement.close();
				} catch(SQLException | NullPointerException e) {}
			}
		}
		return category;
	}

	@Override
	public void update(TasksCategory category) throws PersistentException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE `tasks_category` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
		try {
			preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setString(1, category.getName());
			TasksCategory parent = category.getParent();
			if(parent != null && parent.getId() != null) {
				preparedStatement.setInt(2, parent.getId());
			} else {
				preparedStatement.setNull(2, Types.INTEGER);
			}
			preparedStatement.setInt(3, category.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {}
			identityMap.clear();
		}
	}

	@Override
	public void delete(Integer id) throws PersistentException {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM `tasks_category` WHERE `id` = ?";
		try {
			preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {}
			identityMap.clear();
		}
	}
	
	public List<TasksCategory> findPossibleParents(int id) throws PersistentException {
		TasksCategory category = identityMap.get(id);
		if(category == null) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String sql = "SELECT `name`, `parent_id` FROM `tasks_category` WHERE `id` != ?";
			try {
				preparedStatement = getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					category = getEntityFactory().create(TasksCategory.class);
					category.setId(id);
					category.setName(resultSet.getString("name"));
					Integer parentId = resultSet.getInt("parent_id");
					if(!resultSet.wasNull()) {
						TasksCategory parent = getEntityFactory().create(TasksCategory.class);
						parent.setId(parentId);
						category.setParent(parent);
					}
					identityMap.put(id, category);
				}
			} catch(SQLException e) {
				throw new PersistentException(e);
			} finally {
				try {
					resultSet.close();
				} catch(SQLException | NullPointerException e) {}
				try {
					preparedStatement.close();
				} catch(SQLException | NullPointerException e) {}
			}
		}
		return (List<TasksCategory>) category;
	}
}
