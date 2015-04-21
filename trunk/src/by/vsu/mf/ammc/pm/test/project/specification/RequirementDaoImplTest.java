package by.vsu.mf.ammc.pm.test.project.specification;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.RequirementDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.SQLException;

public class RequirementDaoImplTest {
    public static void main( String[] args ) throws PersistentException, SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance( );
        try {
            connectionPool.init( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000 );
        } catch ( PersistentException e ) {
            e.printStackTrace( );
        }
        System.out.println( "TestCreate" );
        String testPassed = "\ttest passed";
        String testFailed = "\ttest failed";
        String testResult = testPassed;
        RequirementDaoImpl requirementDao = new RequirementDaoImpl( );
        requirementDao.setEntityFactory( new EntityFactory( ) );
        requirementDao.setConnection( connectionPool.getConnection( ) );
        requirementDao.getConnection( ).prepareStatement( "use pm_db" ).executeQuery( );
        Requirement requirement = new Requirement( );
        requirement.setName( "TestRequirement" );
        requirement.setDescription( "It's test requirement" );
        requirement.setImportance( new Float( 1.0 ) );
        requirement.setChangeProbability( new Float( 0.5 ) );
        requirement.setUseCase( null );
        requirement.setModule( null );
        try {
            requirementDao.create( requirement );
        } catch ( PersistentException e ) {
            testResult = testFailed;
        }

        System.out.println( testResult );
        testResult = testPassed;

        System.out.println( "TestRead" );
        try {
            requirementDao.read( 1 );
        } catch ( PersistentException e ) {
            testResult = testFailed;
        }

        System.out.println( testResult );
        testResult = testPassed;

        System.out.println( "TestUpdate" );
        Requirement updatedRequirement = new Requirement( );
        updatedRequirement.setId( 1 );
        updatedRequirement.setName( "UpdatedRequirement" );
        updatedRequirement.setDescription( "It's updated requirement" );
        updatedRequirement.setImportance( new Float( 0.5 ) );
        updatedRequirement.setChangeProbability( new Float( 0.3 ) );
        updatedRequirement.setUseCase( null );
        updatedRequirement.setModule( null );
        try {
            requirementDao.update( updatedRequirement );
        } catch ( PersistentException e ) {
            testResult = testFailed;
        }

        System.out.println( testResult );
        testResult = testPassed;

        System.out.println( "TestDelete" );
        try {
            requirementDao.delete( 1 );
        } catch ( PersistentException e ) {
            testResult = testFailed;
        }
        System.out.println( testResult );


        connectionPool.destroy( );
    }
}

