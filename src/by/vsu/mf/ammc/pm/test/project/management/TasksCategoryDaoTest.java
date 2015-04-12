package by.vsu.mf.ammc.pm.test.project.management;

import java.sql.Connection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TasksCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TasksCategoryDaoTest {
	public static void main(String[] args) throws PersistentException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection connection = pool.getConnection();

		TasksCategoryDaoImpl dao = new TasksCategoryDaoImpl();
		dao.setConnection(connection);
		dao.setEntityFactory(new EntityFactory());

		TasksCategory category1 = null;
		TasksCategory category2 = null;
		int id;
		category1 = new TasksCategory();
		category1.setName("QQQQQ");
		id = dao.create(category1);
		category1.setId(id);
		System.out.println("create tc1 id = " + id);

		category2 = new TasksCategory();
		category2.setName("WWWWW");
		category2.setParent(category1);
		id = dao.create(category2);
		category2.setId(id);
		System.out.println("create tc2 id = " + id);

		category2 = dao.read(id);
		System.out.println("read tc2: name = " + category2.getName());

		category2.setName("EEEEE");
		dao.update(category2);
		System.out.println("update tc2: name = " + dao.read(id).getName());

		dao.delete(category2.getId());
		System.out.println("delete tc2: done");

		dao.delete(category1.getId());
		System.out.println("delete tc1: done");
	}
}
