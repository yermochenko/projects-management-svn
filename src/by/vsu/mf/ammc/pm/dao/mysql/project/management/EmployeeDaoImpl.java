package by.vsu.mf.ammc.pm.dao.mysql.project.management;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.EmployeeDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by meskill on 19.3.15.
 */
public class EmployeeDaoImpl extends BaseDaoImpl implements EmployeeDao {
	@Override
	public Integer create(Employee employee) throws PersistentException {
		String sql = "INSERT INTO `employee` (`user_id`, `team_id`, `role`) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, employee.getUser().getId());
			statement.setInt(2, employee.getTeam().getId());
			statement.setByte(3, (byte)employee.getRole().ordinal());
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
	public Employee read(Integer id) throws PersistentException {
		String sql = "SELECT `user_id`, `team_id`, `role` FROM `employee` WHERE `id` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt( 1, id );
			resultSet = statement.executeQuery( );
			Employee employee = null;
			if(resultSet.next()) {
				employee = new Employee();
				employee.setId(id);
				User user = new User();
				user.setId(resultSet.getInt("user_id"));
				employee.setUser(user);
				Team team = new Team();
				team.setId(resultSet.getInt("team_id"));
				employee.setTeam(team);
				employee.setRole(EmployeesRole.values()[resultSet.getByte("role")]);
			}
			return employee;
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
	public void update(Employee employee) throws PersistentException {
		String sql = "UPDATE `employee` SET `user_id` = ?, `team_id` = ?, `role` = ? WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, employee.getUser().getId());
			statement.setInt(2, employee.getTeam().getId());
			statement.setByte(3, (byte)employee.getRole().ordinal());
			statement.setInt(4, employee.getId());
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
		String sql = "DELETE FROM `employee` WHERE `id` = ?";
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

	@Override
	public Map< Project, List< EmployeesRole > > readByUser( User user ) {
		String sql = "SELECT project.id, project.name, employee.role FROM employee " +
				"INNER JOIN team ON employee.team_id = team.id " +
				"LEFT JOIN project ON project.id = team.project_id " +
				"WHERE employee.user_id = ? ";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection( ).prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			statement.setInt( 1, user.getId( ) );
			resultSet = statement.executeQuery( );
			Map< Project, List< EmployeesRole > > resultMap = new HashMap<>( );
			while ( resultSet.next( ) ) {
				Project project = new Project( );
				project.setId( resultSet.getInt( "project.id" ) );
				if ( resultMap.get( project ) == null ) {
					resultMap.put( project, new LinkedList< EmployeesRole >( ) );
				}
				resultMap.get( project ).add( EmployeesRole.getByIdentity( resultSet.getInt("employee.role") ) );
			}
			return resultMap;
		} catch ( SQLException e ) {
			e.printStackTrace( );
		}
		return null;
	}
}



