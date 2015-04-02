package by.vsu.mf.ammc.pm.test.project.management;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.TeamDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.ActorDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TeamIdentityMapTest {	

	public static void main(String[] args) throws PersistentException, SQLException {
	
		Logger root = Logger.getRootLogger();
		Layout layout = new PatternLayout("%n%d%n%p\t%C.%M:%L%n%m%n");
		root.addAppender(new ConsoleAppender(layout));
		root.setLevel(Level.ALL);
		
		 ConnectionPool pool = ConnectionPool.getInstance();
	     pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
	     TeamDaoImpl dao = new TeamDaoImpl();
	     Connection connection = pool.getConnection();
	     connection.createStatement().execute("use pm_db");
	     dao.setConnection(connection);
	          
	  // create
	  		Team create_team = new Team();
	  		int create_id = -1;
	  		User leader = new User();
	  		leader.setId(11003);
	  		Project project = new Project();
	  		project.setId(11000);
	  		create_team.setLeader(leader);
	  		create_team.setProject(project);
	  		create_id = dao.create(create_team);
	
	  		// read
	  		Team read_team = dao.read(create_id);
	  		
	  		// read hash
	  		Team read_team_hash = dao.read(create_id);
	  		if(read_team_hash.getLeader().getId().equals(read_team.getLeader().getId()) &&
	  				read_team_hash.getProject().getId().equals(read_team.getProject().getId())){
	  			System.out.println("reading success");
	  		}
	  		else{
	  			System.out.println("Failed on read");
	  		}	  		
	  		
	  		// update
	  		Team update_team = new Team();
	  		leader.setId(13002);
	  		project.setId(13000);
	  		update_team.setId(create_id);
	  		update_team.setLeader(leader);
	  		update_team.setProject(project);
	  		dao.update(update_team);
	  		read_team = dao.read(create_id);
	  		
	  		if(update_team.getLeader().getId().equals(read_team.getLeader().getId()) &&
	  				update_team.getProject().getId().equals(read_team.getProject().getId())){
	  			System.out.println("updating success");
	  		}
	  		else{
	  			System.out.println("Failed on update");
	  		}
	  		
	  		// delete
	  		dao.delete(create_id);
	  		
	  		if(dao.read(create_id) == null){
	  			System.out.println("deleting success");
	  		}
	  		else{
	  			System.out.println("Failed on delete");
	  		}
	  		    
	}
}
