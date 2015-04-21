package by.vsu.mf.ammc.pm.dao.mysql.project.management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TeamDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TeamDaoImpl extends BaseDaoImpl implements TeamDao {
	private HashMap<Integer, Team> identityMap = new HashMap<>();

	@Override
	public Integer create(Team team) throws PersistentException {
		String sql = "INSERT INTO `team` (`project_id`, `leader_id`) VALUES (?, ?)";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, team.getProject().getId());
			preparedStatement.setInt(2, team.getLeader().getId());
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
	public Team read(Integer id) throws PersistentException {
		Team team = identityMap.get(id);
		if(team == null) {
			String sql = "SELECT `project_id`, `leader_id` FROM `team` WHERE `id` = ?";
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					team = getEntityFactory().create(Team.class);
					team.setId(id);
					Project project = getEntityFactory().create(Project.class);
					project.setId(resultSet.getInt("project_id"));
					team.setProject(project);
					User leader = getEntityFactory().create(User.class);
					leader.setId(resultSet.getInt("leader_id"));
					team.setLeader(leader);
					identityMap.put(id, team);
				}
				return team;
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
		return team;
	}

	@Override
	public void update(Team team) throws PersistentException {
		String sql = "UPDATE `team` SET `project_id` = ?, `leader_id` = ? WHERE `id` = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, team.getProject().getId());
			preparedStatement.setInt(2, team.getLeader().getId());
			preparedStatement.setInt(3, team.getId());
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
		String sql = "DELETE FROM `team` WHERE `id` = ?";
		PreparedStatement preparedStatement = null;
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
}
