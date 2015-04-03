package by.vsu.mf.ammc.pm.test.project.management;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class TasksCategoryIdentityMapTest {
	public static final String LOG_FILE_NAME = "log.txt";
	public static final Level LOG_LEVEL = Level.ALL;
	public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";

	public static void main(String[] args) throws PersistentException, SQLException, IOException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
		root.addAppender(new FileAppender(layout, LOG_FILE_NAME, true));
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(LOG_LEVEL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
		Connection conn = pool.getConnection();
		conn.createStatement().execute("use pm_db");

		TasksCategoryDaoImpl dao = new TasksCategoryDaoImpl();
		dao.setConnection(conn);
		
		try {
            List<TasksCategory> list = new ArrayList<TasksCategory>();
            //create
            for(int i=0; i<3; i++){
                TasksCategory tc = new TasksCategory();
                tc.setName("tc" + (i+1));
                System.out.println("create tc id = " + dao.create(tc));
                list.add(tc);
            }

            //read
            for(int i=0; i<3; i++){
                TasksCategory tc = dao.read(list.get(i).getId());
                System.out.println("read: id = " + tc.getId() + ", name = " + tc.getName());
            }
            
            //update
            for(int i=0; i<3; i++){
                list.get(i).setName("new_tc" + (i+1));
                dao.update(list.get(i));
            }

            //read
            for(int i=0; i<3; i++){
                TasksCategory tc = dao.read(list.get(i).getId());
                System.out.println("read: id = " + tc.getId() + ", name = " + tc.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
