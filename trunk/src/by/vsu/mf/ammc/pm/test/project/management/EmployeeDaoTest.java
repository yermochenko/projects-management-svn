package by.vsu.mf.ammc.pm.test.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class EmployeeDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 1, 1, 0);
		EmployeeDaoImpl dao = new EmployeeDaoImpl();
		Connection connection = pool.getConnection();
		dao.setConnection(connection);

		// create
		Employee employee = new Employee();
		User user = new User();
		user.setId(13001);
		Team team = new Team();
		team.setId(13001);
		employee.setUser(user);
		employee.setTeam(team);
		employee.setRole(EmployeesRole.BUSINESS_ANALYST);
		Integer id = dao.create(employee);
		System.out.printf("new employee created [id=%d]\n", id);

		// read
		employee = dao.read(id);
		System.out.printf("employee readed [user_id=%d, team_id=%d, role=%s]\n", employee.getUser().getId(), employee.getTeam().getId(), employee.getRole());

		// update
		employee.getUser().setId(13002);
		employee.getTeam().setId(13002);
		employee.setRole(EmployeesRole.PROGRAMMER);
		dao.update(employee);
		employee = dao.read(id);
		System.out.printf("updated employee readed [user_id=%d, team_id=%d, role=%s]\n", employee.getUser().getId(), employee.getTeam().getId(), employee.getRole());

		// delete
		dao.delete(id);
		employee = dao.read(id);
		if(employee == null) {
			System.out.println("employee deleted OK");
		}

		connection.close();
	}
}
