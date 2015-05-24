package by.vsu.mf.ammc.pm.test.junit.project;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.ModuleDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitModuleTest {

	private ModuleDaoImpl createDao(Connection connection) {    
		ModuleDaoImpl dao = new ModuleDaoImpl();
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
		ModuleDaoImpl dao = null;
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		Module mod_created = dao.getEntityFactory().create(Module.class);
		mod_created.setName("test module");
//		System.out.println(mod_created.getAcceptDate());
		Module parent = mod_created.getParent();
		parent.setId(14058);
		mod_created.setParent(parent);
		
		Project project = new Project();
		project.setId(14058);
		Integer id = null;
		try {
			id = dao.create(mod_created);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(id);
		Module mod_readed = null;
		try {
			mod_readed  = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		mod_created.setId(id);
		for(Method method : Module.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(mod_created), method.invoke(mod_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		Module mod_updated = dao.getEntityFactory().create(Module.class);
		mod_updated.setId(id);
		mod_updated.setName("second test module");
		Module parent1 = mod_updated.getParent();
		parent1.setId(14059);
		mod_updated.setParent(parent1);
		Project project1 = new Project();
		project1.setId(14059);
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.update(mod_updated);
			mod_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		for(Method method : Module.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(mod_updated), method.invoke(mod_readed));
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
			mod_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNull(mod_readed);
	}
	
}
