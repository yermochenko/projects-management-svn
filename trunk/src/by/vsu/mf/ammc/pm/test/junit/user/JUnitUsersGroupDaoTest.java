package by.vsu.mf.ammc.pm.test.junit.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitUsersGroupDaoTest {

	private UserDaoImpl createDao(Connection connection) {    // need this for reset Identity map which returns old objects..
		UserDaoImpl dao = new UserDaoImpl();
		dao.setConnection(connection);
		dao.setEntityFactory(new EntityFactory());
		return dao;
	}

	@Test public void testCRUD() {
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 5, 5, 0);
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		UserDaoImpl dao = null;
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		User user_created = dao.getEntityFactory().create(User.class);
		user_created.setName("test user");
		user_created.setPassword("123456");
		user_created.setFirstName("test first");
		user_created.setMiddleName("test middle");
		user_created.setLastName("test last");
		user_created.setAdmin(false);
		UsersGroup groups = new UsersGroup();
		groups.setId(8001);
		user_created.setGroup(groups);
		Integer id = null;
		try {
			id = dao.create(user_created);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(id);
		User user_readed = null;
		try {
			user_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		user_created.setId(id);
		for(Method method : Task.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(user_created), method.invoke(user_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		User user_updated = dao.getEntityFactory().create(User.class);
		user_updated.setId(id);
		user_updated.setName("another user");
		user_updated.setPassword("123336");
		user_updated.setFirstName("test first");
		user_updated.setMiddleName("test middle");
		user_updated.setLastName("test last");
		user_updated.setAdmin(true);
		UsersGroup group_updated = new UsersGroup();
		group_updated.setId(8002);
		user_updated.setGroup(group_updated);
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.update(user_updated);
			user_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		for(Method method : Task.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(user_updated), method.invoke(user_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			dao=createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.delete(id);
			user_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNull(user_readed);
	}

}
