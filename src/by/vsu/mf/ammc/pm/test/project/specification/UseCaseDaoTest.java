package by.vsu.mf.ammc.pm.test.project.specification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.UseCaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UseCaseDaoTest {
	public static final Level LOG_LEVEL = Level.ALL;
	public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";

	public static void main(String[] args) throws PersistentException, SQLException, IOException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(LOG_LEVEL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection conn = pool.getConnection();

		UseCaseDaoImpl dao = null;

		dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		UseCase useCase = new UseCase();
		useCase.setName("name1");
		Module module = new Module();
		module.setId(5001);
		useCase.setModule(module);
		int id = dao.create(useCase);
		System.out.println("create id = " + id);

		dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		useCase = dao.read(id);
		System.out.println("read: name = " + useCase.getName());

		dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		useCase.setName("name2");
		dao.update(useCase);
		System.out.println("update: name = " + dao.read(id).getName());

		dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		dao.delete(useCase.getId());
		System.out.println("delete: done");

		dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		System.out.println("use case = " + dao.read(id));
	}
}
