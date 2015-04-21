package by.vsu.mf.ammc.pm.test.project.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TeamDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TeamIdentityMapTest {
	public static void main(String[] args) throws PersistentException, SQLException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);

		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection connection = pool.getConnection();

		TeamDaoImpl dao = new TeamDaoImpl();
		dao.setEntityFactory(new EntityFactory());
		dao.setConnection(connection);

		// create
		Team team = new Team();
		int id;
		User leader = new User();
		leader.setId(11003);
		Project project = new Project();
		project.setId(11000);
		team.setLeader(leader);
		team.setProject(project);
		id = dao.create(team);
		System.out.println("Create team [id = " + id + "]");

		// read
		team = dao.read(id);
		System.out.println("Read team [project id = " + team.getProject().getId() + "; leader id = " + team.getLeader().getId() + "]");

		// update
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `team` SET `project_id` = ?, `leader_id` = ? WHERE `id` = ?");
		preparedStatement.setInt(1, 11000);
		preparedStatement.setInt(2, 11002);
		preparedStatement.setInt(3, id);
		preparedStatement.executeUpdate();
		preparedStatement.close();

		// read
		team = dao.read(id);
		System.out.println("Read updated team [project id = " + team.getProject().getId() + "; leader id = " + team.getLeader().getId() + "]");

		// delete
		dao.delete(id);
		System.out.println("Delete team. Team is " + dao.read(id));
	}
}
