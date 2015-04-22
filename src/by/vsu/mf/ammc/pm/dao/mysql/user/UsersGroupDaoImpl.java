package by.vsu.mf.ammc.pm.dao.mysql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import by.vsu.mf.ammc.pm.dao.abstraction.user.UsersGroupDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UsersGroupDaoImpl extends BaseDaoImpl implements UsersGroupDao {
	
	@Override
	public Integer create(UsersGroup entity) throws PersistentException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO `users_group` (`name`, `parent_id`) VALUES (?, ?)";
			ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			// подготавливаем запрос
			ps.setString(1, entity.getName());
			
			UsersGroup parent = entity.getParent();
			if(parent != null && parent.getId() != null){
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()){
				return rs.getInt(1);
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
	public UsersGroup read(Integer id) throws PersistentException {
		UsersGroup ug = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT `name`, `parent_id` FROM `users_group` WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql);
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			// заполняем объект полученными полями
			if(rs.next()){
				ug = new UsersGroup();
				ug.setId(id);
				ug.setName(rs.getString("Name"));
				Integer parentId = rs.getInt("parent_id");
				
				if(!rs.wasNull() && parentId != id){
					UsersGroup parent = read(parentId);
					ug.setParent(parent);
				}
			}
			
			return ug;
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
	public void update(UsersGroup entity) throws PersistentException {
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `users_group` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql);
			
			ps.setString(1, entity.getName());
			UsersGroup parent = entity.getParent();
			if(parent != null && parent.getId() != null) {
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			ps.setInt(3, entity.getId());
			
			ps.executeUpdate();
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
			String sql = "DELETE FROM `users_group` WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				ps.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
}
