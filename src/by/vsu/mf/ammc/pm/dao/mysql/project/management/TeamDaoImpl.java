package by.vsu.mf.ammc.pm.dao.mysql.project.management;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TeamDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TeamDaoImpl extends BaseDaoImpl implements TeamDao{	
	@Override
	public Integer create(Team entity) throws PersistentException {
		String sql = "INSERT INTO `team` (`project_id`, `leader_id`) VALUES (?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, entity.getProject().getId());
			statement.setInt(2, entity.getLeader().getId());			
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
	public Team read(Integer id) throws PersistentException {
		String sql = "SELECT `id`, `project_id`, `leader_id` FROM `team` WHERE `id` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			Team team = null;
			if(resultSet.next()) {
				team = new Team();
				team.setId(id);
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                team.setProject(project);
                User leader = new User();
                leader.setId(resultSet.getInt("id"));
                team.setLeader(leader);
			}
			return team;
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
	public void update(Team entity) throws PersistentException {
		String sql = "UPDATE `team` SET `project_id` = ?, `leader_id` = ? WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, entity.getProject().getId());
			statement.setInt(2, entity.getLeader().getId());
			statement.setInt(3, entity.getId());
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
		String sql = "DELETE FROM `team` WHERE `id` = ?";
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
