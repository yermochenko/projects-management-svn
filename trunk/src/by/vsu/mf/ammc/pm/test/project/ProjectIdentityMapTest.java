package by.vsu.mf.ammc.pm.test.project;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.project.ProjectDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ProjectIdentityMapTest {

	public static void main(String[] args) throws PersistentException, SQLException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);

		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
		ProjectDaoImpl dao = new ProjectDaoImpl();
		Connection connection = pool.getConnection();
		connection.createStatement().execute("use pm_db");
		dao.setConnection(connection);
		
		// create
		Project createProject = new Project();
		int create_id = -1;
		createProject.setId(7211);
		createProject.setName("Created name");
		createProject.setDescription("Create discription");
		ProjectsCategory pc = new ProjectsCategory();
		pc.setId(7203);
		createProject.setCategory(pc);
		User user = new User();
		user.setId(7001);
		createProject.setManager(user);
		create_id = dao.create(createProject);
		assert create_id != -1 : "Failed on create project";
		
		// read
		Project readProject = dao.read(create_id);
		assert readProject.getName().equals(createProject.getName()) && readProject.getDescription().equals(createProject.getDescription()) && readProject.getCategory().getId().equals(createProject.getCategory().getId()) && readProject.getManager().getId().equals(createProject.getManager().getId()) : "Failed on read project";

		// read hash
		Project read_project_hash = dao.read(create_id);
		if(read_project_hash.getName().equals(readProject.getName()) && read_project_hash.getDescription().equals(readProject.getDescription()) && read_project_hash.getCategory().getId().equals(readProject.getCategory().getId()) && read_project_hash.getManager().getId().equals(readProject.getManager().getId())) {
			System.out.println("Reading success");
		}
		else {
			System.out.println("Failed on read");
		}
		
		// update
		createProject.setName("Name for update");
		dao.update(createProject);
		readProject = dao.read(create_id);
		assert readProject.getName().equals(createProject.getName()) && readProject.getDescription().equals(createProject.getDescription()) && readProject.getCategory().getId().equals(createProject.getCategory().getId()) && readProject.getManager().getId().equals(createProject.getManager().getId()) : "Failed on update project";
		
		// delete
		dao.delete(create_id);
		assert dao.read(create_id) == null : "Failed on delete project";
		}
}
