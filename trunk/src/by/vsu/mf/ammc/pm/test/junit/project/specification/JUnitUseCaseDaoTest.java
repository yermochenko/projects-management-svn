package by.vsu.mf.ammc.pm.test.junit.project.specification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.domain.project.specification.UseCasesRelationsType;
import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.UseCaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitUseCaseDaoTest extends Assert {
	@Test
	public void testCRUD() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		} catch(SQLException e) {
			e.printStackTrace();
		}

		UseCaseDaoImpl useCaseDaoImpl = new UseCaseDaoImpl();
		useCaseDaoImpl.setConnection(connection);
		useCaseDaoImpl.setEntityFactory(new EntityFactory());

		UseCase useCase = useCaseDaoImpl.getEntityFactory().create(UseCase.class);
		Integer moduleId = 13188;
		useCase.setName("testActor");
		Module module = new Module();
		module.setId(moduleId);
		useCase.setModule(module);

		try {
			connection.prepareStatement("use pm_db").executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		// create
		int useCaseId = 0;
		try {
			useCaseId = useCaseDaoImpl.create(useCase);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		// read
		UseCase UseCaseReaded = null;
		try {
			UseCaseReaded = useCaseDaoImpl.read(useCaseId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		useCase.setId(useCaseId);
		Assert.assertEquals(useCase.getName(), UseCaseReaded.getName());
		Assert.assertEquals(useCase.getModule(), UseCaseReaded.getModule());

		// update
		try {
			useCase.setName("NewName");
			useCaseDaoImpl.update(useCase);
			UseCaseReaded = useCaseDaoImpl.read(useCaseId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertEquals(useCase.getName(), UseCaseReaded.getName());

		// delete
		try {
			useCaseDaoImpl.delete(useCaseId);
			UseCaseReaded = useCaseDaoImpl.read(useCaseId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertNull(UseCaseReaded);

	}

}
