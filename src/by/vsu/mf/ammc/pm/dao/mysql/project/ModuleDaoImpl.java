package by.vsu.mf.ammc.pm.dao.mysql.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ModuleDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ModuleDaoImpl extends BaseDaoImpl implements ModuleDao {

    Map<Integer, Module> identityMap = new HashMap<>();

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
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                identityMap.put(id, module);
                return id;
            } else {
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Module read(Integer id) throws PersistentException {
        if (identityMap.containsKey(id)) return identityMap.get(id);
        String sql = "SELECT `name`, `parent_id`, `project_id` FROM `module` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Module module = null;
            if (resultSet.next()) {
                module = getEntityFactory().create(Module.class);
                module.setId(id);
                module.setName(resultSet.getString("name"));
                Project pr = getEntityFactory().create(Project.class);
                pr.setId(resultSet.getInt("project_id"));
                module.setProject(pr);
                Module modulee = getEntityFactory().create(Module.class);
                modulee.setId(resultSet.getInt("parent_id"));
                module.setParent(modulee);
            }
            return module;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
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
            statement.setInt(4, module.getId());
            statement.executeUpdate();
            identityMap.put(module.getId(), module);
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
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
            identityMap.remove(id);
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

	@Override
	public ArrayList<Module> read(Project project) throws PersistentException {
		Integer id = project.getId();
		String sql = "SELECT `name`, `parent_id`, `project_id` FROM `module` WHERE `project_id` = ?";
		PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            ArrayList<Module> modules = new ArrayList<Module>();
            while (resultSet.next()) {
                Module module = getEntityFactory().create(Module.class);
                module.setId(resultSet.getInt("id"));
                module.setName(resultSet.getString("name"));
                module.setProject(project);
                Module modulee = getEntityFactory().create(Module.class);
                modulee.setId(resultSet.getInt("parent_id"));
                module.setParent(modulee);
                modules.add(module);
            }
            return modules;
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
	}
}
