package by.vsu.mf.ammc.pm.test.project.management;

import java.sql.Connection;
import java.sql.SQLException;


import by.vsu.mf.ammc.pm.dao.mysql.project.management.TeamDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TeamDaoTest {
	  public static void main(String[] args) throws PersistentException, SQLException {
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
	        assert create_id != -1 : "Failed on create";

	        //read
	        Team read_team = dao.read(create_id);
	        assert create_team.getLeader().getId() == read_team.getLeader().getId() &&
	               create_team.getProject().getId() == read_team.getProject().getId()
	               : "Failed on read";

	        //update
	        Team update_team = new Team();
	        leader.setId(11002);
	        project.setId(11000);
	        update_team.setId(create_id);
	        update_team.setLeader(leader);
	        update_team.setProject(project);	        
	        dao.update(update_team);
	        read_team = dao.read(create_id);
	        assert update_team.getLeader().getId() == read_team.getLeader().getId() &&
	               update_team.getProject().getId() == read_team.getProject().getId()
	               : "Failed on update";

	        //delete
	        dao.delete(create_id);
	        assert dao.read(create_id) == null : "Failed on delete";
	    }
}
