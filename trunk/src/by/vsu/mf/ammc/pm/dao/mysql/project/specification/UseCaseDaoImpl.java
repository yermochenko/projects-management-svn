package by.vsu.mf.ammc.pm.dao.mysql.project.specification;

import by.vsu.mf.ammc.pm.dao.abstraction.project.specification.UseCaseDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCasesRelationsType;
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
				int useCaseId = resultSet.getInt(1);
				String sql_relations = "INSERT INTO `use_cases_relation` (`source_id`, `destination_id`, `type`) VALUES (?, ?, ?)";
				preparedStatement = getConnection().prepareStatement(sql_relations);
				UseCasesRelationsType[] types = UseCasesRelationsType.values();
				for(int i = 0; i < types.length; i++) {
					for(UseCase useCaseRelate : useCase.getRelations(types[i])) {
						preparedStatement.setInt(1, useCaseId);
						preparedStatement.setInt(2, useCaseRelate.getId());
						preparedStatement.setInt(3, i);
						preparedStatement.executeUpdate();
					}
				}

				return useCaseId;
			} else {
				throw new PersistentException();
			}
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {
			}
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {
			}
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

					String sql_relations = "SELECT `destination_id`, `type` FROM `use_cases_relation` WHERE `source_id` = ?";
					preparedStatement = getConnection().prepareStatement(sql_relations);
					preparedStatement.setInt(1, id);
					resultSet = preparedStatement.executeQuery();
					while(resultSet.next()) {
						int source_id = resultSet.getInt("destination_id");
						int type = resultSet.getInt("type");
						useCase.getRelations(UseCasesRelationsType.values()[type]).add(this.read(source_id));
					}

					identityMap.put(id, useCase);
				}
			} catch(SQLException e) {
				throw new PersistentException(e);
			} finally {
				try {
					resultSet.close();
				} catch(SQLException | NullPointerException e) {
				}
				try {
					preparedStatement.close();
				} catch(SQLException | NullPointerException e) {
				}
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

			String sql_relations = "DELETE FROM `use_cases_relation` WHERE `source_id` = ?";
			preparedStatement = getConnection().prepareStatement(sql_relations);
			preparedStatement.setInt(1, useCase.getId());
			preparedStatement.executeUpdate();
			sql_relations = "INSERT INTO `use_cases_relation` (`source_id`, `destination_id`, `type`) VALUES (?, ?, ?)";
			preparedStatement = getConnection().prepareStatement(sql_relations);
			UseCasesRelationsType types[] = UseCasesRelationsType.values();
			for(int i = 0; i < types.length; i++) {
				for(UseCase useCaseRelate : useCase.getRelations(types[i])) {
					preparedStatement.setInt(1, useCase.getId());
					preparedStatement.setInt(2, useCaseRelate.getId());
					preparedStatement.setInt(3, i);
					preparedStatement.executeUpdate();
				}
			}

		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {
			}
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
			throw new PersistentException(e);
		} finally {
			try {
				preparedStatement.close();
			} catch(SQLException | NullPointerException e) {
			}
			identityMap.clear();
		}
	}
}
