package by.vsu.mf.ammc.pm.dao.mysql.project.management;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TasksCategoryDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class TasksCategoryDaoImpl extends BaseDaoImpl implements TasksCategoryDao {
    private Map<Integer, TasksCategory> map = new HashMap<Integer, TasksCategory>();

    @Override
    public Integer create(TasksCategory entity) throws PersistentException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO `tasks_category` (`name`, `parent_id`) VALUES (?, ?)";
            ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            TasksCategory parent = entity.getParent();
            if(parent != null && parent.getId() != null) {
                ps.setInt(2, parent.getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
                map.put(id, entity);
                return id;
            } else {
                // TODO logger.error
                throw new PersistentException();
            }
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                rs.close();
            } catch(SQLException | NullPointerException e) {}
            try {
                ps.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public TasksCategory read(Integer id) throws PersistentException {
        TasksCategory tc = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if((tc = map.get(id)) != null) {
            return tc;
        }
        try {
            String sql = "SELECT `name`, `parent_id` FROM `tasks_category` WHERE `id` = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                tc = getEntityFactory().create(TasksCategory.class);;
                tc.setId(id);
                tc.setName(rs.getString("Name"));
                Integer parentId = rs.getInt("parent_id");
                if(!rs.wasNull() && parentId != id) {
                    TasksCategory parent = read(parentId);
                    tc.setParent(parent);
                }
            }
            if(tc != null){
                map.put(id, tc);
            }
            return tc;
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                rs.close();
            } catch(SQLException | NullPointerException e) {}
            try {
                ps.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void update(TasksCategory entity) throws PersistentException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE `tasks_category` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, entity.getName());
            TasksCategory parent = entity.getParent();
            if(parent != null && parent.getId() != null) {
                ps.setInt(2, parent.getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
            map.put(entity.getId(), entity);
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                ps.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM `tasks_category` WHERE `id` = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            map.remove(id);
        } catch(SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                ps.close();
            } catch(SQLException | NullPointerException e) {}
        }
    }
}
