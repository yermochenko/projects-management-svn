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
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TasksCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TasksCategoryIdentityMapTest {
	private static TasksCategory read(int id, Connection con) throws PersistentException {
		TasksCategory category = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT `name`, `parent_id` FROM `tasks_category` WHERE `id` = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				category = new TasksCategory();
				category.setId(id);
				category.setName(resultSet.getString("name"));
				Integer parentId = resultSet.getInt("parent_id");
				if(!resultSet.wasNull()) {
					TasksCategory parent = new TasksCategory();
					parent.setId(parentId);
					category.setParent(parent);
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
		return category;
	}

	public static void main(String[] args) throws PersistentException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection conn = pool.getConnection();

		TasksCategoryDaoImpl dao = new TasksCategoryDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());

		// create
		TasksCategory category = new TasksCategory();
		category.setName("Test parent category");
		category.setId(dao.create(category));
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			TasksCategory tc = new TasksCategory();
			tc.setName("tc" + (i + 1));
			tc.setParent(category);
			list.add(dao.create(tc));
		}

		// read without identity map
		long start1, finish1;
		start1 = System.currentTimeMillis();
		for(Integer id : list) {
			TasksCategory tc = read(id, conn);
			read(tc.getParent().getId(), conn);
			//System.out.println("read: id = " + tc.getId() + ", name = " + tc.getName() + ", parent = " + read(tc.getParent().getId(), conn).getName());
		}
		finish1 = System.currentTimeMillis();

		// read with identity map
		long start2, finish2;
		start2 = System.currentTimeMillis();
		for(Integer id : list) {
			TasksCategory tc = dao.read(id);
			dao.read(tc.getParent().getId());
			//System.out.println("read: id = " + tc.getId() + ", name = " + tc.getName() + ", parent = " + dao.read(tc.getParent().getId()).getName());
		}
		finish2 = System.currentTimeMillis();

		System.out.println("Reading without identity map " + (finish1 - start1) + " ms");
		System.out.println("Reading with identity map " + (finish2 - start2) + " ms");
	}
}
