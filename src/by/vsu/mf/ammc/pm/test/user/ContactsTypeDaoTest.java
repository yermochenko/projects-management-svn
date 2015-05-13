package by.vsu.mf.ammc.pm.test.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ContactsTypeDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException, IOException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection conn = pool.getConnection();

		ContactsTypeDaoImpl dao = new ContactsTypeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		ContactsType contactsType = new ContactsType();
		contactsType.setId(12100);
		contactsType.setName("phone");
		contactsType.setRegexp("^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$");
		int id = dao.create(contactsType);
		System.out.println("create id = " + id);

		dao = new ContactsTypeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		contactsType = dao.read(id);

		System.out.println("read: name = " + contactsType.getName());

		dao = new ContactsTypeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		contactsType.setName("phone123");
		dao.update(contactsType);
		System.out.println("update: name = " + dao.read(id).getName());

		dao = new ContactsTypeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		dao.delete(contactsType.getId());
		System.out.println("delete: done");

		dao = new ContactsTypeDaoImpl();
		dao.setConnection(conn);
		dao.setEntityFactory(new EntityFactory());
		System.out.println("contactsType = " + dao.read(id));
	}
}
