package by.vsu.mf.ammc.pm.test.junit.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TaskDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.domain.project.management.TasksStatus;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;

/**
 * Created by meskill on 14.05.15.
 */
public class JUnitTaskDaoTest {

	private TaskDaoImpl createDao(Connection connection) {    // need this for reset Identity map which returns old objects..
		TaskDaoImpl dao = new TaskDaoImpl();
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
		TaskDaoImpl dao = null;
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		Task task_created = dao.getEntityFactory().create(Task.class);
		task_created.setName("test task");
		task_created.setDescription("description");
		task_created.setPlanTime(100);
		task_created.setDifficulty((float)0.3);
		task_created.setOpenDate(new Date(400000000000L));
		task_created.setAcceptDate(new Date(500000000000L));
		System.out.println(task_created.getAcceptDate());
		TasksCategory taskCategory = new TasksCategory();
		taskCategory.setId(5001);
		task_created.setCategory(taskCategory);
		Requirement requirement = new Requirement();
		requirement.setId(5001);
		Employee employee = new Employee();
		employee.setId(5001);
		task_created.setEmployee(employee);
		task_created.setStatus(TasksStatus.NEW);
		Integer id = null;
		try {
			id = dao.create(task_created);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(id);
		Task task_readed = null;
		try {
			task_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		task_created.setId(id);
		for(Method method : Task.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(task_created), method.invoke(task_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		Task task_updated = dao.getEntityFactory().create(Task.class);
		task_updated.setId(id);
		task_updated.setName("another task");
		task_updated.setDescription("another description");
		task_updated.setPlanTime(30);
		task_updated.setDifficulty((float)0.444);
		task_updated.setOpenDate(new Date((long)10000));
		task_updated.setAcceptDate(new Date((long)300000));
		taskCategory = new TasksCategory();
		taskCategory.setId(5002);
		task_updated.setCategory(taskCategory);
		requirement = new Requirement();
		requirement.setId(5002);
		employee = new Employee();
		employee.setId(5002);
		task_updated.setEmployee(employee);
		task_updated.setStatus(TasksStatus.NEW);
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.update(task_updated);
			task_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		for(Method method : Task.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(task_updated), method.invoke(task_readed));
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
			task_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNull(task_readed);
	}
}
