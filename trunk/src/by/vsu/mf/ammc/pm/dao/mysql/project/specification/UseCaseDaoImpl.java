package by.vsu.mf.ammc.pm.dao.mysql.project.specification;

import by.vsu.mf.ammc.pm.dao.abstraction.project.specification.UseCaseDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.*;
import java.util.HashMap;

public class UseCaseDaoImpl extends BaseDaoImpl implements UseCaseDao {
	private static final String createSql = "insert into `use_case` (`name`, `module_id`) values (?, ?)";
	private static final String readSql = "select * from `use_case` where `id` = ?;";
	private static final String updateSql = "update `use_case` set `name`=? , `module_id`=? where `id`=?";
	private static final String deleteSql = "delete from `use_case` where `id` = ?;";
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static HashMap<Integer, UseCase> identityMap = new HashMap<Integer, UseCase>();

	@Override
	public Integer create(UseCase entity) throws PersistentException {
		try {
			preparedStatement = getConnection().prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setInt(2, entity.getModule().getId());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				int result = resultSet.getInt(1);
				entity.setId(result);
				identityMap.put(result, entity);
				return result;
			} else {
				throw new PersistentException();
			}
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				if(resultSet != null)
					resultSet.close();
			} catch(SQLException e) {}
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			} catch(SQLException e) {}
		}
	}

	@Override
	public UseCase read(Integer id) throws PersistentException {
		UseCase useCase = identityMap.get(id);
		if (useCase == null) {
			try {
				preparedStatement = getConnection().prepareStatement(readSql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				useCase = getEntityFactory().create(UseCase.class);
				useCase.setId(id);
				if(resultSet.next()) {
					useCase.setName(resultSet.getString("name"));
					Module module = getEntityFactory().create(Module.class);
					module.setId(resultSet.getInt("module_id"));
					useCase.setModule(module);
				}
				identityMap.put(id, useCase);
			} catch(SQLException e) {
				throw new PersistentException();
			} finally {
				try {
					if(resultSet != null)
						resultSet.close();
				} catch(SQLException e) {}
				try {
					if(preparedStatement != null)
						preparedStatement.close();
				} catch(SQLException e) {}
			}
		}
		return useCase;
	}

	@Override
	public void update(UseCase entity) throws PersistentException {
		try {
			preparedStatement = getConnection().prepareStatement(updateSql);
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setInt(2, entity.getModule().getId());
			preparedStatement.setInt(3, entity.getId());
			if(0 >= preparedStatement.executeUpdate()) {
				throw new PersistentException();
			} else {
				identityMap.remove(entity.getId());
				identityMap.put(entity.getId(), entity);
			}
		} catch(SQLException e) {
			throw new PersistentException();
		} finally {
			try {
				if(resultSet != null)
					resultSet.close();
			} catch(SQLException e) {}
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			} catch(SQLException e) {}
		}
	}

	@Override
	public void delete(Integer id) throws PersistentException {
		identityMap.remove(id);
		try {
			preparedStatement = getConnection().prepareStatement(deleteSql);
			preparedStatement.setInt(1, id);
			if(0 >= preparedStatement.executeUpdate()) {
				throw new PersistentException();
			}
		} catch(SQLException e) {
			throw new PersistentException();
		} finally {
			try {
				if(resultSet != null)
					resultSet.close();
			} catch(SQLException e) {}
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			} catch(SQLException e) {}
		}
	}

}
