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
import by.vsu.mf.ammc.pm.dao.mysql.user.*;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ContactDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException, IOException {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 1, 1, 0);
		Connection connection = pool.getConnection();

		ContactDaoImpl daoContact = new ContactDaoImpl();
		daoContact.setConnection(connection);
		daoContact.setEntityFactory(new EntityFactory());
		Contact contact = new Contact();
		contact.setId(200000);
		contact.setName("contact1");
		User user = new User();
		user.setId(20000);
		user.setName("user1");
		user.setPassword("pass");
		user.setFirstName("Zimnickaya");
		user.setMiddleName("Alena");
		user.setLastName("Victorovna");
		user.setAdmin(false);
		UsersGroup userGroup = new UsersGroup();
		userGroup.setId(2000000);
		userGroup.setName("group");
		user.setGroup(userGroup);
		contact.setUser(user);
		ContactsType type = new ContactsType();
		type.setId(20000);
		type.setName("name");
		type.setRegexp("reg");
		contact.setType(type);
		int id = daoContact.create(contact);
		System.out.println("create id = " + id);

		daoContact = new ContactDaoImpl();
		daoContact.setConnection(connection);
		daoContact.setEntityFactory(new EntityFactory());
		contact = daoContact.read(id);
		System.out.println("read: name = " + contact.getName());

		daoContact = new ContactDaoImpl();
		daoContact.setConnection(connection);
		daoContact.setEntityFactory(new EntityFactory());
		contact.setName("contact2");
		daoContact.update(contact);
		System.out.println("update: name = " + daoContact.read(id).getName());

		daoContact = new ContactDaoImpl();
		daoContact.setConnection(connection);
		daoContact.setEntityFactory(new EntityFactory());
		daoContact.delete(contact.getId());
		System.out.println("delete: done");

		daoContact = new ContactDaoImpl();
		daoContact.setConnection(connection);
		daoContact.setEntityFactory(new EntityFactory());
		System.out.println("module = " + daoContact.read(id));
	}
}
