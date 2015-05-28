package by.vsu.mf.ammc.pm.test.project.management;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;


public class EmployeeIdentityMapTest {
	private static Employee read(int id, Connection con) throws PersistentException {
		Employee employee = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT `user_id`, `team_id`, `role` FROM `employee` WHERE `id` = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				employee = new Employee();
				employee.setId(id);
				employee.setRole(EmployeesRole.valueOf("role"));
				Integer user_id = resultSet.getInt("user_id");
				if(!resultSet.wasNull()) {
					User user = new User();
					user.setId(user_id);
					employee.setUser(user);
				}
				Integer team_id = resultSet.getInt("team_id");
				if(!resultSet.wasNull()) {
					Team team = new Team();
					team.setId(team_id);
					employee.setTeam(team);
				}
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
		}
		return employee;
	}

	public static void main(String[] args) throws PersistentException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection conn = pool.getConnection();

		EmployeeDaoImpl dao = new EmployeeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());

		//create
		Employee employee = new Employee();
		employee.setId(dao.create(employee));
		User user = new User();
		Team team = new Team();
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			Employee empl = new Employee();
			empl.setUser(user);
			empl.setTeam(team);
			empl.setRole(EmployeesRole.BUSINESS_ANALYST);
			list.add(dao.create(empl));
		}

		// read without identity map
		long start1, finish1;
		start1 = System.currentTimeMillis();
		for(Integer id : list) {
			Employee empl = read(id, conn);
			read(empl.getId(), conn);}
		finish1 = System.currentTimeMillis();

		// read with identity map
		long start2, finish2;
		start2 = System.currentTimeMillis();
		for(Integer id : list) {
			Employee empl = dao.read(id);
			dao.read(empl.getId());}
		finish2 = System.currentTimeMillis();

		System.out.println("Reading without identity map " + (finish1 - start1) + " ms");
		System.out.println("Reading with identity map " + (finish2 - start2) + " ms");
	}
}