package by.vsu.mf.ammc.pm.dispatcher.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.TransactionImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.ServiceFactory;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactsTypeService;
import by.vsu.mf.ammc.pm.service.main.ServiceFactoryImpl;

public class ContactsTypeSaveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ContactsTypeSaveServlet.class);

	public static final String LOG_FILE_NAME = "log.txt";
	public static final Level LOG_LEVEL = Level.ALL;
	public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";

	public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/pm_db?useUnicode=true&characterEncoding=UTF-8";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";
	public static final int DB_POOL_START_SIZE = 10;
	public static final int DB_POOL_MAX_SIZE = 1000;
	public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

	public void init() {
		try {
			Logger root = Logger.getRootLogger();
			Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
			root.addAppender(new FileAppender(layout, LOG_FILE_NAME, true));
			root.addAppender(new ConsoleAppender(layout));
			root.setLevel(LOG_LEVEL);
			ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
		} catch(PersistentException | IOException e) {
			logger.error("It is impossible to initialize application", e);
			destroy();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServiceFactory factory = null;
		try {
			String parameter = request.getParameter("identity");
			logger.debug(String.format("Parameter value: " + parameter));
			Integer identity = null;
			if(parameter != null && parameter != "") {
				identity = Integer.parseInt(request.getParameter("identity"));
			}
			String name = request.getParameter("name");
			String regexp = request.getParameter("regexp");
			logger.debug(String.format("contactsType ID value: " + identity));
			logger.debug(String.format("contactsType name value: " + name));
			logger.debug(String.format("contactsType regexp value: " + regexp));

			factory = new ServiceFactoryImpl(new TransactionImpl());
			ContactsTypeService contactsTypeService = factory.getService(ContactsTypeService.class);

			ContactsType type;
			if(identity != null) {
				type = contactsTypeService.findById(identity);
				logger.debug(String.format("contactsType ID value: " + type.getId()));
			} else {
				logger.debug(String.format("Creating new instance"));
				type = new ContactsType();
			}
			type.setName(name);
			type.setRegexp(regexp);
			logger.debug(String.format("contactsType value: " + type));
			logger.debug(String.format("contactsType ID value: " + type.getId()));
			logger.debug(String.format("contactsType name value: " + type.getName()));
			logger.debug(String.format("contactsType regexp value: " + type.getRegexp()));

			contactsTypeService.save(type);

			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/admin/contacts-type/list.html");

		} catch(PersistentException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
