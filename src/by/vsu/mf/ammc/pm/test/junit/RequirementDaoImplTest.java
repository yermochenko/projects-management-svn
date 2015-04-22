package by.vsu.mf.ammc.pm.test.junit;


import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.RequirementDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import org.junit.Test;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequirementDaoImplTest {
    @Test
    public void testCRUD( ) {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000 );
        } catch (PersistentException e) {
            e.printStackTrace();
        }
        RequirementDaoImpl requirementDao = new RequirementDaoImpl();
        requirementDao.setEntityFactory( new EntityFactory() );
        try {
            requirementDao.setConnection( connectionPool.getConnection( ) );
            requirementDao.getConnection().prepareStatement( "use pm_db" ).executeQuery();
        } catch ( SQLException | PersistentException e ) {
            e.printStackTrace( );
        }
        Requirement requirement = new Requirement();
        Integer workingIdentifier = null;
        requirement.setName( "TestRequirement" );
        requirement.setDescription("It's test requirement");
        requirement.setImportance( new Float( 1.0 ) );
        requirement.setChangeProbability( new Float( 0.5 ) );
        requirement.setUseCase( null );
        requirement.setModule( null );
        try {
            workingIdentifier = requirementDao.create( requirement );
            requirement.setId( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( requirement.getId(), requirementDao.read( workingIdentifier ).getId() );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        Requirement updatedRequirement = new Requirement();
        updatedRequirement.setId( workingIdentifier );
        updatedRequirement.setName("UpdateRequirement");
        updatedRequirement.setDescription("It's updated requirement");
        updatedRequirement.setImportance(new Float(0.5));
        updatedRequirement.setChangeProbability(new Float(0.3));
        updatedRequirement.setUseCase(null);
        updatedRequirement.setModule(null);
        try {
            requirementDao.update( updatedRequirement );
        } catch (PersistentException e) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( updatedRequirement, requirementDao.read( workingIdentifier ) );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            requirementDao.delete( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            // Sql statement have used instead read method, because we use the cache map in it.
            String sql = " SELECT name, description, importance, change_probability, use_case_id, module_id FROM requirement WHERE id = ? ";
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = requirementDao.getConnection( ).prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
            preparedStatement.setInt( 1, workingIdentifier );
            resultSet = preparedStatement.executeQuery( );
            Assert.assertFalse( resultSet.next( ) );
        } catch ( SQLException e ) {
            Assert.assertTrue( false );
        }


    }
}