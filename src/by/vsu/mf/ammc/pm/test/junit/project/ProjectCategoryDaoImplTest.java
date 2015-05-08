package by.vsu.mf.ammc.pm.test.junit.project;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.ProjectsCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectCategoryDaoImplTest {
    @Test
    public void testCRUD( ) {

        ConnectionPool connectionPool = ConnectionPool.getInstance( );
        try {
            connectionPool.init( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000 );
        } catch ( PersistentException e ) {
            e.printStackTrace( );
        }
        ProjectsCategoryDaoImpl projectsCategoryDao = new ProjectsCategoryDaoImpl( );
        projectsCategoryDao.setEntityFactory( new EntityFactory( ) );
        try {
            projectsCategoryDao.setConnection( connectionPool.getConnection( ) );
            projectsCategoryDao.getConnection( ).prepareStatement( "use pm_db" ).executeQuery( );
        } catch ( SQLException | PersistentException e ) {
            e.printStackTrace( );
        }
        ProjectsCategory projectsCategory = new ProjectsCategory();
        Integer workingIdentifier = null;
        projectsCategory.setName( "TestProjectCategory" );

        try {
            workingIdentifier = projectsCategoryDao.create( projectsCategory );
            projectsCategory.setId( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( projectsCategory.getId( ), projectsCategoryDao.read( workingIdentifier ).getId( ) );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        ProjectsCategory updatedProjectCategory = new ProjectsCategory();
        updatedProjectCategory.setName( "UpdatedTestProjectCategory" );

        try {
            projectsCategoryDao.update( updatedProjectCategory );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( updatedProjectCategory, projectsCategoryDao.read( workingIdentifier ) );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            projectsCategoryDao.delete( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            // Sql statement have used instead read method, because we use the cache map in it.
            String sql = " SELECT name FROM projects_category WHERE id = ? ";
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = projectsCategoryDao.getConnection( ).prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
            preparedStatement.setInt( 1, workingIdentifier );
            resultSet = preparedStatement.executeQuery( );
            Assert.assertFalse( resultSet.next( ) );
        } catch ( SQLException e ) {
            Assert.assertTrue( false );
        }


    }
}

