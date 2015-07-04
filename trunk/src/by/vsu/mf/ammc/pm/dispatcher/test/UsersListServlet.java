package by.vsu.mf.ammc.pm.dispatcher.test;

import by.vsu.mf.ammc.pm.dao.mysql.TransactionImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.ServiceFactory;
import by.vsu.mf.ammc.pm.service.abstraction.user.UserService;
import by.vsu.mf.ammc.pm.service.abstraction.user.UsersGroupService;
import by.vsu.mf.ammc.pm.service.main.ServiceFactoryImpl;
import org.apache.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersListServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(UsersListServlet.class);

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

    //private List<User> users = new ArrayList();

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
            factory = new ServiceFactoryImpl(new TransactionImpl());
            UserService userService = factory.getService(UserService.class);
            UsersGroupService usersGroupService = factory.getService(UsersGroupService.class);

            Integer id = null;
            String strId = request.getParameter("id");
            if(strId != null && strId.length() > 0)
                id = Integer.parseInt(strId);
            else{
                for(UsersGroup usersGroup : usersGroupService.findAll())
                    if(usersGroup.getParent() != null) {
                        id = usersGroup.getId();
                        break;
                    }
                if(id == null)
                    //throw new PersistentException();
                    id = 21026;
            }
            logger.debug(String.format("id usersGroup: " + id));
            UsersGroup usersGroup = usersGroupService.findById(id);
            usersGroup.getChildren();
            logger.debug(String.format("groupName: " + usersGroup.getName()));

            List<UsersGroup> usersGroups = usersGroupService.findAll();
            List<User> users = new ArrayList();
            addUsers(usersGroups, usersGroup, users, userService);
            //logger.debug(String.format("size users: " + users.size()));

            List<User> list = userService.findByUsersGroup(usersGroup);
            logger.debug(String.format("size users: " + list.size()));
            users.addAll(list);
            //List<User> users = userService.findByUsersGroup(usersGroup);
            //logger.debug(String.format("size : " + users.size()));

            request.setAttribute("users", users);
            request.setAttribute("groupName", usersGroup.getName());
            request.setAttribute("usersGroups", usersGroups);

        } catch (PersistentException e) {
            e.printStackTrace();
        }finally {
            factory.close();
        }
        String jspPage = "/WEB-INF/jsp/admin/user/list.jsp";
        getServletContext().getRequestDispatcher(jspPage).forward(request, response);
    }
    private void addUsers(List<UsersGroup> usersGroups, UsersGroup parentGroup, List<User> users, UserService userService){
        for(UsersGroup group : usersGroups){
            logger.debug(String.format("group parent: " + group.getParent()));
            if(group.getParent() != null && group.getParent().getId().equals(parentGroup.getId())){
                try {
                    List<User> list = userService.findByUsersGroup(group);
                    logger.debug(String.format("size users: " + list.size()));
                    users.addAll(list);
                    addUsers(usersGroups, group, users, userService);
                } catch (PersistentException e) {
                    e.printStackTrace();
                }
            }
            /*if(){
                logger.debug(String.format("group: " + group.getParent().getId()));
                logger.debug(String.format("parent_group: " + parentGroup.getId()));

            }*/
        }
    }
}
