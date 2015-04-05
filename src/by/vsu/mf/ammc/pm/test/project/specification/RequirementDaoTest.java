package by.vsu.mf.ammc.pm.test.project.specification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.RequirementDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class RequirementDaoTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, PersistentException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		
		EntityFactory entityFactory = new EntityFactory();
		RequirementDaoImpl requirementDaoImpl = new RequirementDaoImpl();
		requirementDaoImpl.setEntityFactory(entityFactory);
		requirementDaoImpl.setConnection(connection);
		PreparedStatement statemanet = connection.prepareStatement("use pm_db");
		statemanet.executeQuery();
		
		
		Requirement requirement = requirementDaoImpl.read(5001);
		if(requirement!=null){
			System.out.println("Requirement created from EntityFactory successfully.");
			System.out.println("\t"+requirement);
		}
		
	}

}
