package by.vsu.mf.ammc.pm.test.junit.project.specification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.ActorDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.specification.Actor;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitActorDaoTest extends Assert {
	@Test
	public void testCRUD() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		} catch(SQLException e) {
			e.printStackTrace();
		}

		ActorDaoImpl actorDaoImpl = new ActorDaoImpl();
		actorDaoImpl.setConnection(connection);
		actorDaoImpl.setEntityFactory(new EntityFactory());

		Actor actor = actorDaoImpl.getEntityFactory().create(Actor.class);
		Integer actorId = 11999;
		Integer projectId = 11999;
		actor.setId(actorId);
		actor.setName("testActor");
		Project project = new Project();
		project.setId(projectId);
		actor.setProject(project);

		try {
			connection.prepareStatement("use pm_db").executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		// create
		try {
			actorDaoImpl.create(actor);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		// read
		Actor actorReaded = null;
		try {
			actorReaded = actorDaoImpl.read(actorId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertEquals(actor.getId(), actorReaded.getId());
		Assert.assertEquals(actor.getName(), actorReaded.getName());
		Assert.assertEquals(actor.getProject(), actorReaded.getProject());

		// update
		try {
			actor.setName("NewName");
			actorDaoImpl.update(actor);
			actorReaded = actorDaoImpl.read(actorId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertEquals(actor.getName(), actorReaded.getName());

		// delete
		try {
			actorDaoImpl.delete(actorId);
			actorReaded = actorDaoImpl.read(actorId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertNull(actorReaded);
	}

}
