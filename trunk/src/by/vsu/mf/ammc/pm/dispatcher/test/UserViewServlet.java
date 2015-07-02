package by.vsu.mf.ammc.pm.dispatcher.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.dao.abstraction.Transaction;
import by.vsu.mf.ammc.pm.dao.mysql.TransactionImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.ServiceFactory;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactService;
import by.vsu.mf.ammc.pm.service.abstraction.user.UserService;
import by.vsu.mf.ammc.pm.service.main.ServiceFactoryImpl;
import by.vsu.mf.ammc.pm.service.main.user.ContactServiceImpl;
import by.vsu.mf.ammc.pm.service.main.user.UserServiceImpl;


public class UserViewServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(UserViewServlet.class);

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
		/*request.setCharacterEncoding("UTF-8");
		List<User> users = new LinkedList<>();
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		userServiceImpl.setTransaction(new TransactionImpl());
		
		users.add(userServiceImpl.findById(Integer.parseInt(request.getParameter("id"))));		
		
		request.setAttribute("users", users);
		String jspPage = "/WEB-INF/jsp/selectData.jsp";
		getServletContext().getRequestDispatcher(jspPage).forward(request,
				response);*/
		ServiceFactory factory = null;
		try {
			factory = new ServiceFactoryImpl(new TransactionImpl());
			UserService userService = factory.getService(UserService.class);
			ContactService contactService = factory.getService(ContactService.class);
			
			
			User user = userService.findById(5001);
			List<Contact> contacts = contactService.getContactByUser(user);
			user.setContacts(contacts);
			request.setAttribute("user", user);
			request.setAttribute("contacts", contacts);			
			
		} catch (PersistentException e) {
			e.printStackTrace();
		}
		
		
		//userService.setTransaction(new Transaction);
		
		
		
		String jspPage = "/WEB-INF/jsp/view.jsp";
		getServletContext().getRequestDispatcher(jspPage).forward(request,
				response);
	}
}