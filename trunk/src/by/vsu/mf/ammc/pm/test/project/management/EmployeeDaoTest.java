package by.vsu.mf.ammc.pm.test.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by meskill on 19.3.15.
 */
public class EmployeeDaoTest {

    public static void main(String[] args) throws PersistentException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
        EmployeeDaoImpl dao = new EmployeeDaoImpl();
        Connection conn = pool.getConnection();
        conn.createStatement().execute("use pm_db");


        dao.setConnection(conn);
        // create
        Employee create_employee = new Employee();
        int create_id = -1;
        User user = new User();
        user.setId(13001);
        Team team = new Team();
        team.setId(13001);
        create_employee.setUser(user);
        create_employee.setTeam(team);
        create_employee.setRole(EmployeesRole.BUSINESS_ANALYST);
        create_id = dao.create(create_employee);
        assert create_id != -1 : "Failed on create";

        //read
        Employee read_employee = dao.read(create_id);
        assert create_employee.getUser().getId() == read_employee.getUser().getId() &&
                create_employee.getTeam().getId() == read_employee.getTeam().getId() &&
                create_employee.getRole() == read_employee.getRole() : "Failed or read";

        //update
        Employee update_employee = new Employee();
        user.setId(13002);
        team.setId(13002);
        update_employee.setId(create_id);
        update_employee.setUser(user);
        update_employee.setTeam(team);
        update_employee.setRole(EmployeesRole.PROJECT_MANAGER);
        dao.update(update_employee);
        read_employee = dao.read(create_id);
        assert update_employee.getUser().getId() == read_employee.getUser().getId() &&
                update_employee.getTeam().getId() == read_employee.getTeam().getId() &&
                update_employee.getRole() == read_employee.getRole() : "Failed on update";

        //delete
        dao.delete(create_id);
        assert dao.read(create_id) == null : "Failed on delete";
    }
}
