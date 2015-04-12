package by.vsu.mf.ammc.pm.test.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.ModuleDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ModuleDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException, IOException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection conn = pool.getConnection();

		ModuleDaoImpl dao = new ModuleDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		Module module = new Module();
		module.setName("mod1");
		Module modulee = new Module();
		modulee.setId(8300);
		module.setParent(modulee);
		Project pr = new Project();
		pr.setId(8801);
		module.setProject(pr);
		int id = dao.create(module);
		System.out.println("create id = " + id);

		dao = new ModuleDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		module = dao.read(id);
		System.out.println("read: name = " + module.getName());

		dao = new ModuleDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		module.setName("mod1");
		dao.update(module);
		System.out.println("update: name = " + dao.read(id).getName());

		dao = new ModuleDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		dao.delete(module.getId());
		System.out.println("delete: done");

		dao = new ModuleDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		System.out.println("module = " + dao.read(id));
	}
}
