package by.vsu.mf.ammc.pm.test.project.management;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.TasksCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TasksCategoryDaoTest {

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
		
		TasksCategoryDaoImpl dao = new TasksCategoryDaoImpl();
		dao.setConnection(connection);
		
		int id1 = 20001;
		TasksCategory tc1 = null;
		try {
			tc1 = new TasksCategory();
			tc1.setName("QQQQQ");
			id1 = dao.create(tc1);
			System.out.println("create tc1 id = " + id1);
		} catch (Exception e) {
			System.err.println("create tc1");
			e.printStackTrace();
		}
		
		int id2 = 20002;
		TasksCategory tc2;
		try {
			tc2 = new TasksCategory();
			tc2.setName("WWWWW");
			tc2.setParent(tc1);
			id2 = dao.create(tc2);
			System.out.println("create tc2 id = " + id2);
		} catch (Exception e) {
			System.err.println("create tc2");
			e.printStackTrace();
		}
		
		tc2 = null;
		try {
			tc2 = dao.read(id2);
			System.out.println("read tc2: name = " + tc2.getName());
		} catch (Exception e) {
			System.err.println("read tc2");
			e.printStackTrace();
		}
		
		try {
			if (tc2 != null) {
				tc2.setName("EEEEE");
				dao.update(tc2);
				System.out.println("update tc2: name = " + dao.read(id2).getName());
			}
		} catch(Exception e) {
			System.err.println("update tc2");
			e.printStackTrace();
		}
		
		try {
			if (tc2 != null) {
				dao.delete(id2);
				System.out.println("delete tc2: done");
			}
		} catch(Exception e) {
			System.err.println("delete tc2");
			e.printStackTrace();
		}
		
		try {
			if (tc1 != null) {
				dao.delete(id1);
				System.out.println("delete tc1: done");
			}
		} catch(Exception e) {
			System.err.println("delete tc1");
			e.printStackTrace();
		}
	}

}
