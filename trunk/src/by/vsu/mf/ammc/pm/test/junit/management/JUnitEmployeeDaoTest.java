package by.vsu.mf.ammc.pm.test.junit.management;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class JUnitEmployeeDaoTest extends Assert {
	@Test
	public void testCRUD() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		} catch(SQLException e) {
			e.printStackTrace();
		}

		EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
		employeeDaoImpl.setConnection(connection);
		employeeDaoImpl.setEntityFactory(new EntityFactory());

		Employee employee = employeeDaoImpl.getEntityFactory().create(Employee.class);
		Integer employeeId = 20000;
		Integer userId = 20000;
		Integer teamId = 20000;
		employee.setId(employeeId);
		User user = new User();
		user.setId(userId);
		user.setName("user");
		user.setPassword("12345");
		user.setFirstName("Troshko");
		user.setMiddleName("Vadim");
		user.setLastName("Vladimirovich");
		user.setAdmin(false);
		Integer usersGroupId = 200001;
		UsersGroup usersGroup = new UsersGroup();
		usersGroup.setId(usersGroupId);
		usersGroup.setName("1");
		usersGroup.setParent(null);
		user.setGroup(usersGroup);
		employee.setUser(user);
		Team team = new Team();
		team.setId(teamId);
		Integer projectId = 20001;
		Project project = new Project();
		project.setId(projectId);
		project.setName("project20001");
		project.setDescription("description");
		Integer projectCategoryId = 20021;
		ProjectsCategory projectCategory = new ProjectsCategory();
		projectCategory.setId(projectCategoryId);
		projectCategory.setName("projectCategory");
		projectCategory.setParent(null);
		project.setCategory(projectCategory);
		Integer managerId = 20061;
		User manager = new User();
		manager.setId(managerId);
		manager.setName("user1");
		manager.setPassword("12345");
		manager.setFirstName("Ulasevich");
		manager.setMiddleName("Dima");
		manager.setLastName("Vladimirovich");
		manager.setAdmin(false);
		user.setGroup(usersGroup);
		project.setManager(manager);
		team.setProject(project);
		Integer leaderId = 20361;
		User leader = new User();
		leader.setId(leaderId);
		leader.setName("user2");
		leader.setPassword("12345");
		leader.setFirstName("Astaschenko");
		leader.setMiddleName("Andre");
		leader.setLastName("Evgen");
		leader.setAdmin(false);
		user.setGroup(usersGroup);
		team.setLeader(leader);
		employee.setTeam(team);
		employee.setRole(EmployeesRole.BUSINESS_ANALYST);
		try {
			connection.prepareStatement("use pm_db").executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		// create
		try {
			employeeDaoImpl.create(employee);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		// read
		Employee employeeReaded = null;
		try {
			employeeReaded = employeeDaoImpl.read(employeeId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}
		Assert.assertEquals(employee.getId(), employeeReaded.getId());
		Assert.assertEquals(employee.getUser(), employeeReaded.getUser());
		Assert.assertEquals(employee.getTeam(), employeeReaded.getTeam());
		Assert.assertEquals(employee.getRole(), employeeReaded.getRole());
		// update
		try {
			employee.setRole(EmployeesRole.PROGRAMMER);
			employeeDaoImpl.update(employee);
			employeeReaded = employeeDaoImpl.read(employeeId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertEquals(employee.getRole(), employeeReaded.getRole());

		// delete
		try {
			employeeDaoImpl.delete(employeeId);
			employeeReaded = employeeDaoImpl.read(employeeId);
		} catch(PersistentException e) {
			Assert.assertTrue(false);
		}

		Assert.assertNull(employeeReaded);
	}

}
