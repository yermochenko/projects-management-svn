package by.vsu.mf.ammc.pm.test.project.specification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.UseCaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UseCaseDaoTest {
	public static final String LOG_FILE_NAME = "log.txt";
	public static final Level LOG_LEVEL = Level.ALL;
	public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";

	public static void main(String[] args) throws PersistentException, SQLException, IOException{
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
		root.addAppender(new FileAppender(layout, LOG_FILE_NAME, true));
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(LOG_LEVEL);
    	ConnectionPool pool = ConnectionPool.getInstance();
        pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
        Connection conn = pool.getConnection();
        conn.createStatement().execute("use pm_db");

        UseCaseDaoImpl dao = new UseCaseDaoImpl();
        dao.setConnection(conn);
        
        //dao.delete(20014);
        int id = 5001;
        try{
        	UseCase useCase = new UseCase();
	        useCase.setName("name1"); 
	        Module module = new Module();
	        module.setId(5001);
	        useCase.setModule(module);
	        id = dao.create(useCase);
	        System.out.println("create id = " + id);
        }catch(Exception e){
        	System.err.println("create with module");
        	e.printStackTrace();
        }
        UseCase useCase = null;
        try{
        	useCase = dao.read(id);
    		System.out.println("read: name = " + useCase.getName());
    		
        }catch(Exception e){
        	System.err.println("read");
        	e.printStackTrace();
        }
        
        try{
        	if(useCase != null){
            	useCase.setName("name2");
            	dao.update(useCase);
        		System.out.println("update: name = " + dao.read(id).getName());        		
        	}
        }catch(Exception e){
        	System.err.println("update");
        	e.printStackTrace();
        }
        
        try{
        	if(useCase != null){
            	dao.delete(useCase.getId());
        		System.out.println("delete: done");        		
        	}
        }catch(Exception e){
        	System.err.println("delete");
        	e.printStackTrace();
        }        
    }
}
