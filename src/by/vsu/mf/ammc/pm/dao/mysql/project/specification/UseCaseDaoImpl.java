package by.vsu.mf.ammc.pm.dao.mysql.project.specification;

import by.vsu.mf.ammc.pm.dao.abstraction.project.specification.UseCaseDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class UseCaseDaoImpl extends BaseDaoImpl implements UseCaseDao {
	private HashMap<Integer, UseCase> identityMap = new HashMap<Integer, UseCase>();

	@Override
	public Integer create(UseCase useCase) throws PersistentException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO `use_case` (`name`, `module_id`) VALUES (?, ?)";
		try {
			preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, useCase.getName());
			preparedStatement.setInt(2, useCase.getModule().getId());
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
	public UseCase read(Integer id) throws PersistentException {
		UseCase useCase = identityMap.get(id);
		if(useCase == null) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			String sql = "SELECT `name`, `module_id` FROM `use_case` WHERE `id` = ?";
			try {
				preparedStatement = getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					useCase = getEntityFactory().create(UseCase.class);
					useCase.setId(id);
					useCase.setName(resultSet.getString("name"));
					Module module = getEntityFactory().create(Module.class);
					module.setId(resultSet.getInt("module_id"));
					useCase.setModule(module);
					identityMap.put(id, useCase);
				}
			} catch(SQLException e) {
				throw new PersistentException();
			} finally {
				try {
					resultSet.close();
				} catch(SQLException | NullPointerException e) {}
				try {
					preparedStatement.close();
				} catch(SQLException | NullPointerException e) {}
			}
		}
		return useCase;
	}

	@Override
	public void update(UseCase useCase) throws PersistentException {
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE `use_case` SET `name` = ?, `module_id` = ? WHERE `id` = ?";
		try {
			preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setString(1, useCase.getName());
			preparedStatement.setInt(2, useCase.getModule().getId());
			preparedStatement.setInt(3, useCase.getId());
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException();
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
		String sql = "DELETE FROM `use_case` WHERE `id` = ?";
		try {
			preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException();
		} finally {
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {}
			identityMap.clear();
		}
	}
}
