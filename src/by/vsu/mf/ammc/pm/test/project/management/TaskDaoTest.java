package by.vsu.mf.ammc.pm.test.project.management;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.TaskDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.domain.project.management.TasksStatus;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TaskDaoTest{
	public static void main(String[] args) throws PersistentException, SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		TaskDaoImpl taskDaoImpl = new TaskDaoImpl();
		taskDaoImpl.setConnection(connection);
		
		Task task = new Task();
		task.setName("test task");
		task.setDescription("description");
		task.setPlanTime(100);
		task.setDifficulty((float) 0.3);
		task.setOpenDate(new Date((long)400000));
		task.setAcceptDate(new Date((long)500000));
		TasksCategory taskCategory = new TasksCategory();
		taskCategory.setId(5001);
		task.setCategory(taskCategory);
		Requirement requirement = new Requirement();
		requirement.setId(5001);
		Employee employee = new Employee();
		employee.setId(5001);
		task.setEmployee(employee);
		task.setStatus(TasksStatus.NEW);

		PreparedStatement statemanet = connection.prepareStatement("use pm_db");
		statemanet.executeQuery();
		System.out.println("test mathod create!____________");
		Integer identity = taskDaoImpl.create(task);
		statemanet = connection.prepareStatement("Select * from task");
		ResultSet resultSet = statemanet.executeQuery();
		while(resultSet.next()){
			System.out.print(
					resultSet.getInt("id")+"_"+
					resultSet.getString("name")+"_"+
					resultSet.getString("description")+"_ "+
					resultSet.getInt("plan_time")+"_"+
					resultSet.getFloat("difficulty")+"_ "+
					resultSet.getDate("open_date")+"_ "+
					resultSet.getDate("accept_date")+"_ "+
					resultSet.getDate("close_date")+"_ "+
					resultSet.getInt("category_id")+"_ "+
					resultSet.getInt("requirement_id")+"_ "+
					resultSet.getString("module_id")+"_ "+
					resultSet.getString("employee_id")+"_ "+
					resultSet.getInt("status"));
			System.out.println();
		}
		
		
		System.out.println("test method read!_________________");
		task = taskDaoImpl.read(identity);
		if(task !=null){
			System.out.println(task.getId()+" "+
					task.getName()+" "+
					task.getDescription());
		}else{
			System.out.println("method read(int id) not work!");
		}
		
		task.setDescription("new update description");
	
		System.out.println("test method update!__________");
		taskDaoImpl.update(task);
		task = taskDaoImpl.read(identity);
		if(task !=null){
			System.out.println(task.getId()+" "+
					task.getName()+" "+
					task.getDescription());
		}else{
			System.out.println("method read(int id) not work!");
		}
		
		System.out.println("test method delete!________________");
		taskDaoImpl.delete(identity);
		statemanet = connection.prepareStatement("Select * from task");
		resultSet = statemanet.executeQuery();
		System.out.println();
		while(resultSet.next()){
			
			System.out.print(
					resultSet.getInt("id")+"_"+
					resultSet.getString("name")+"_"+
					resultSet.getString("description")+"_ "+
					resultSet.getInt("plan_time")+"_"+
					resultSet.getFloat("difficulty")+"_ "+
					resultSet.getDate("open_date")+"_ "+
					resultSet.getDate("accept_date")+"_ "+
					resultSet.getDate("close_date")+"_ "+
					resultSet.getInt("category_id")+"_ "+
					resultSet.getInt("requirement_id")+"_ "+
					resultSet.getString("module_id")+"_ "+
					resultSet.getString("employee_id")+"_ "+
					resultSet.getInt("status"));
			System.out.println();
		}
		
		
		
	}

}
