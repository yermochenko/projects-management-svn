package by.vsu.mf.ammc.pm.test.junit.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitContactsTypeDaoTest {

	@Test
	public void testCRUD() {
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		ConnectionPool cp = ConnectionPool.getInstance();
		try {
			cp.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 5, 5, 0);
		} catch (PersistentException e) {
			System.out.println("Cannon initialise connection pool!");
			e.printStackTrace();
			return;
		}
		System.out.println("Connection pool has initialised.");
		Connection conn = null;
		try {
			conn = cp.getConnection();
		} catch (PersistentException e) {
			System.out.println("Cannon create connection!");
			e.printStackTrace();
			return;
		}
		System.out.println("Connection has created.");
		/*Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot create statement!");
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
		System.out.println("Statement has created.");
		try {
			st.execute("use pm_db");
		} catch (SQLException e1) {
			System.out.println("Cannot execute statement!");
			StatementClose(st, conn);
			ConnectionClose(conn);
			e1.printStackTrace();
			return;
		}
		System.out.println("Use pm_db.");
		StatementClose(st, conn);*/
		ContactsTypeDaoImpl ctdi = new ContactsTypeDaoImpl();
		ctdi.setConnection(conn);
		ctdi.setEntityFactory(new EntityFactory());
		
		ContactsType contactsType1 = ctdi.getEntityFactory().create(ContactsType.class);
		contactsType1.setId(20018);
		contactsType1.setName("Telephone");
		contactsType1.setRegexp("nn-nn-nn");
		/*Read(ctdi, conn, 20018, contactsType1);
		Delete(ctdi, conn, 20018);*/
		int ctid = Create(ctdi, conn, contactsType1);
		Read(ctdi, conn, ctid, contactsType1);
		contactsType1.setName("Cellphone");
		contactsType1.setRegexp("nnn-nn-nn");
		Update(ctdi, conn, ctid, contactsType1);
		Delete(ctdi, conn, ctid);
		ConnectionClose(conn);
	}
	
	private static void ConnectionClose(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Cannot close connection!");
			e.printStackTrace();
			return;
		}
	}
	
	/*private static void StatementClose(Statement st, Connection conn) {
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println("Cannot close statement!");
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
	}*/
	
	private static int Create(ContactsTypeDaoImpl ctdi, Connection conn, ContactsType contactsType1) {
		int ctid = -1;
		try {
			ctid = ctdi.create(contactsType1);
		} catch (PersistentException e) {
			System.out.println("Cannot create entity!");
			ConnectionClose(conn);
			e.printStackTrace();
			return -1;
		}
		if (ctid == -1) {
			System.out.println("Entity has not created!");
			ConnectionClose(conn);
			return -1;
		}
		System.out.println("Entity has been created.");
		return ctid;
	}
	
	private static void Read(ContactsTypeDaoImpl ctdi, Connection conn, int ctid, ContactsType contactsType1) {
		System.out.println("ContactsType1.");
		System.out.println("Id: " + contactsType1.getId());
		System.out.println("Name: " + contactsType1.getName());
		System.out.println("Regexp: " + contactsType1.getRegexp());
		ContactsType contactsType2 = null;
		try {
			contactsType2 = ctdi.read(ctid);
		} catch (PersistentException e) {
			System.out.println("Cannot read entity!");
			Delete(ctdi, conn, ctid);
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
		if (contactsType2 == null) {
			System.out.println("Entity has not been read!");
			Delete(ctdi, conn, ctid);
			ConnectionClose(conn);
			return;
		}
		System.out.println("Entity has been read.");
		System.out.println("Id: " + contactsType2.getId());
		System.out.println("Name: " + contactsType2.getName());
		System.out.println("Regexp: " + contactsType2.getRegexp());
		if (!contactsType2.getName().equals(contactsType1.getName()) || !contactsType2.getRegexp().equals(contactsType1.getRegexp())) {
			System.out.println("Wrong entity!");
			Delete(ctdi, conn, ctid);
			ConnectionClose(conn);
			return;
		}
	}
	
	private static void Update(ContactsTypeDaoImpl ctdi, Connection conn, int ctid, ContactsType contactsType1) {
		try {
			ctdi.update(contactsType1);
		} catch (PersistentException e) {
			System.out.println("Cannot update entity!");
			Delete(ctdi, conn, ctid);
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
		System.out.println("Entity has been updated.");
		Read(ctdi, conn, ctid, contactsType1);
	}
	
	private static void Delete(ContactsTypeDaoImpl ctdi, Connection conn, int ctid) {
		ContactsType contactsType1 = null;
		try {
			ctdi.delete(ctid);
		} catch (PersistentException e) {
			System.out.println("Cannot delete entity!");
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
		try {
			contactsType1 = ctdi.read(ctid);
		} catch (PersistentException e) {
			System.out.println("Cannot read entity!");
			ConnectionClose(conn);
			e.printStackTrace();
			return;
		}
		if (contactsType1 != null) {
			System.out.println("Entity has not been deleted!");
			ConnectionClose(conn);
			return;
		}
	}
}
