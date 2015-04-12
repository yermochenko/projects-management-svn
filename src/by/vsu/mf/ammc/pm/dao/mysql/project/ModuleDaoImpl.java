package by.vsu.mf.ammc.pm.dao.mysql.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ModuleDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ModuleDaoImpl  extends BaseDaoImpl implements ModuleDao {
	@Override
	public Integer create(Module module) throws PersistentException {
		String sql = "INSERT INTO `module` (`name`, `parent_id`, `project_id`) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, module.getName());
			statement.setInt(2, module.getParent().getId());
			statement.setInt(3, module.getProject().getId());
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
	public Module read(Integer id) throws PersistentException {
		String sql = "SELECT `name`, `parent_id`, `project_id` FROM `module` WHERE `id` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			Module module = null;
			if(resultSet.next()) {
				module = new Module();
				module.setId(id);
				module.setName(resultSet.getString("name"));
				Project pr = getEntityFactory().create(Project.class);
				pr.setId(resultSet.getInt("project_id"));
				module.setProject(pr);
				Module modulee = getEntityFactory().create(Module.class);
				modulee.setId(resultSet.getInt("module_id"));
				module.setParent(modulee);
			}
			return module;
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
	public void update(Module module) throws PersistentException {
		String sql = "UPDATE `module` SET `name` = ?, `parent_id` = ?, `project_id` = ? WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, module.getName());
			statement.setInt(2, module.getParent().getId());
			statement.setInt(3, module.getProject().getId());
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
		String sql = "DELETE FROM `module` WHERE `id` = ?";
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
}
