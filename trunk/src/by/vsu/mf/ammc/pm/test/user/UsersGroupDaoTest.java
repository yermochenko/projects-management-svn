package by.vsu.mf.ammc.pm.test.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.UsersGroupDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UsersGroupDaoTest {

	public static final String LOG_FILE_NAME = "log.txt";
	public static final Level LOG_LEVEL = Level.ALL;
	public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";
	
	public static void main(String[] args) throws IOException, PersistentException, SQLException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
		root.addAppender(new FileAppender(layout, LOG_FILE_NAME, true));
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(LOG_LEVEL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
		Connection connection = pool.getConnection();
		connection.createStatement().execute("use pm_db");
		
		UsersGroupDaoImpl dao = new UsersGroupDaoImpl();
		dao.setConnection(connection);
		dao.setEntityFactory(new EntityFactory());
		
		int id1 = 15001;
		
		UsersGroup ug1 = null;
		
		try {
			ug1 = dao.getEntityFactory().create(UsersGroup.class);
			ug1.setName("p31");
			id1 = dao.create(ug1);
			System.out.println("create ug1 id = " + id1);
		} catch (Exception e) {
			System.err.println("create ug1.");
			e.printStackTrace();
		}
		
		int id2 = 15002;
		UsersGroup ug2;
		try {
			ug2 = dao.getEntityFactory().create(UsersGroup.class);
			ug2.setName("p41");
			ug2.setParent(ug1);
			id2 = dao.create(ug2);
			System.out.println("create ug2 id = " + id2);
		} catch (Exception e) {
			System.err.println("create ug2");
			e.printStackTrace();
		}
		
		ug2 = null;
		try {
			ug2 = dao.read(id2);
			System.out.println("read ug2: name = " + ug2.getName());
		} catch (Exception e) {
			System.err.println("read ug2");
			e.printStackTrace();
		}
		
		try {
			if (ug2 != null) {
				ug2.setName("41");
				dao.update(ug2);
				System.out.println("update ug2: name = " + dao.read(id2).getName());
			}
		} catch(Exception e) {
			System.err.println("update ug2");
			e.printStackTrace();
		}
		
		try {
			if (ug2 != null) {
				dao.delete(id2);
				System.out.println("delete ug2: done");
			}
		} catch(Exception e) {
			System.err.println("delete ug2");
			e.printStackTrace();
		}
		
		try {
			if (ug1 != null) {
				dao.delete(id1);
				System.out.println("delete ug1: done");
			}
		} catch(Exception e) {
			System.err.println("delete ug1");
			e.printStackTrace();
		}
	}

}
