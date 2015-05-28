package by.vsu.mf.ammc.pm.test.junit.project.management;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TeamDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitTeamDaoTest {
	
	private TeamDaoImpl createDao(Connection connection) {    
		TeamDaoImpl dao = new TeamDaoImpl();
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
		TeamDaoImpl dao = null;
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		Team team_created = dao.getEntityFactory().create(Team.class);
		
		//System.out.println(task_created.getAcceptDate());
		
		Project project = new Project();
		project.setId(15001);
		team_created.setProject(project);
		
		User user = new User();
		user.setId(5001);
		team_created.setLeader(user);
		
		Integer id = null;
		try {
			id = dao.create(team_created);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNotNull(id);
		
		Team team_readed = null;
		try {
			team_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		team_created.setId(id);
		for(Method method : Team.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(team_created), method.invoke(team_readed));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				} catch(InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		Team team_updated = dao.getEntityFactory().create(Team.class);

		project = new Project();
		project.setId(15002);
		team_updated.setProject(project);
		
		user = new User();
		user.setId(15002);
		team_updated.setLeader(user);
		
		try {
			dao = createDao(pool.getConnection());
		} catch(PersistentException e) {
			e.printStackTrace();
		}
		try {
			dao.update(team_updated);
			team_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		for(Method method : Team.class.getMethods()) {
			if(method.getName().startsWith("get") && !method.getName().endsWith("Date")) {
				try {
					Assert.assertEquals(method.invoke(team_updated), method.invoke(team_readed));
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
			team_readed = dao.read(id);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertNull(team_readed);
	}
}
