package by.vsu.mf.ammc.pm.test.project.specification;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.project.specification.ActorDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.specification.Actor;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ActorIdentityMapTest {

    public static void main(String[] args) throws PersistentException, SQLException {
	Logger root = Logger.getRootLogger();
	Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
	root.addAppender(new ConsoleAppender(layout));
	root.setLevel(Level.ALL);

	ConnectionPool pool = ConnectionPool.getInstance();
	pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
	ActorDaoImpl dao = new ActorDaoImpl();
	Connection connection = pool.getConnection();
	connection.createStatement().execute("use pm_db");
	dao.setConnection(connection);

	// create
	Actor createActor = new Actor();
	int create_id = -1;
	createActor.setId(42005);
	createActor.setName("Created name");
	Project pr = new Project();
	pr.setId(11000);
	createActor.setProject(pr);
	create_id = dao.create(createActor);
	assert create_id != -1 : "Failed on create actor";

	// read
	Actor readActor = dao.read(create_id);
	assert readActor.getName().equals(createActor.getName()) && readActor.getProject().getId().equals(createActor.getProject().getId()) : "Failed on read actor";

	// read hash
	Actor read_actor_hash = dao.read(create_id);
	if (read_actor_hash.getName().equals(readActor.getName()) && read_actor_hash.getProject().getId().equals(readActor.getProject().getId())) {
	    System.out.println("reading success");
	} else {
	    System.out.println("Failed on read");
	}

	// update
	createActor.setName("Some updated name");
	dao.update(createActor);
	readActor = dao.read(create_id);
	assert readActor.getName().equals(createActor.getName()) && readActor.getProject().getId().equals(createActor.getProject().getId()) : "Failed on update actor";

	// delete
	dao.delete(create_id);
	assert dao.read(create_id) == null : "Failed on delete actor";
    }

}
