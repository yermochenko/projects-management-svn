package by.vsu.mf.ammc.pm.test.project.specification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.project.management.TaskDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.RequirementDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.domain.project.management.TasksStatus;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class RequirementIdentityMapTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, PersistentException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
		
		RequirementDaoImpl requirementDaoImpl = new RequirementDaoImpl();
		requirementDaoImpl.setConnection(connection);

		PreparedStatement statemanet = connection.prepareStatement("use pm_db");
		statemanet.executeQuery();
		
		
		
		String sql = " SELECT name, description, importance, change_probability, use_case_id, module_id FROM requirement WHERE id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Requirement requirement = null;
        long timeWithoutCaching = System.currentTimeMillis();
        int i=0;
		int id = 5001;
		while(i<100){
	            preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS);
	            preparedStatement.setInt( 1 , id);
	            resultSet = preparedStatement.executeQuery();
	            requirement = new Requirement();
	            if( resultSet.next() ) {
	                requirement.setId( id );
	                requirement.setName( resultSet.getString("name") );
	                requirement.setDescription( resultSet.getString( "description") );
	                requirement.setImportance( resultSet.getFloat( "importance" ) );
	                requirement.setChangeProbability( resultSet.getFloat( "change_probability" ) );
	                Integer useCaseId = resultSet.getInt( "use_case_id" );
	                if( !resultSet.wasNull() ) {
	                    UseCase useCase = new UseCase();
	                    useCase.setId( useCaseId );
	                    requirement.setUseCase( useCase );
	                }
	                Integer moduleId = resultSet.getInt( "module_id" );
	                if( !resultSet.wasNull() ) {
	                    Module module = new Module();
	                    module.setId(moduleId);
	                    requirement.setModule(module);
	                }
	            }
	            i++;
		}
		timeWithoutCaching = System.currentTimeMillis() - timeWithoutCaching;
		
		long timeWithCashing = System.currentTimeMillis();
		while(i >0){
			requirement = requirementDaoImpl.read(id);
			i--;
		}
		timeWithCashing = System.currentTimeMillis() - timeWithCashing;
		System.out.println("time with caching: "+timeWithCashing);
		System.out.println("time without caching: "+timeWithoutCaching);
		System.out.println(timeWithoutCaching - timeWithCashing);
		
		

		
	}

}
