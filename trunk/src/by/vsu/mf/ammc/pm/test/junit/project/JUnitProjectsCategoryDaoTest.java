package by.vsu.mf.ammc.pm.test.junit.project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.ProjectsCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitProjectsCategoryDaoTest {
	
	private ProjectsCategoryDaoImpl createDao(Connection connection) {    
		ProjectsCategoryDaoImpl dao = new ProjectsCategoryDaoImpl();
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
		ProjectsCategoryDaoImpl dao = null;
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		ProjectsCategory pc_create = dao.getEntityFactory().create(ProjectsCategory.class);
		pc_create.setName("test project category");
		ProjectsCategory parent = pc_create.getParent();
		parent.setId(7201);
		pc_create.setParent(parent);
		Integer id = null;
		try {
			id = dao.create(pc_create);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(id);
		
		ProjectsCategory pc_readed = null;
		try {
			pc_readed  = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		pc_create.setId(id);
		for(Method method : ProjectsCategory.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(pc_create), method.invoke(pc_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		ProjectsCategory pc_updated = dao.getEntityFactory().create(ProjectsCategory.class);
		pc_updated.setId(id);
		pc_updated.setName("second test module");
		ProjectsCategory parent1 = pc_updated.getParent();
		parent1.setId(7203);
		pc_updated.setParent(parent1);
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.update(pc_updated);
			pc_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		for(Method method : ProjectsCategory.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(pc_updated), method.invoke(pc_readed));
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
			pc_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNull(pc_readed);
	}		
}