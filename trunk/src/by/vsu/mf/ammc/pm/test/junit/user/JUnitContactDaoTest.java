package by.vsu.mf.ammc.pm.test.junit.user;


import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactDaoImpl;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JUnitContactDaoTest {
    @Test
    public void testCRUD( ) {

    	ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000 );
        } catch (PersistentException e) {
            e.printStackTrace();
        }
        ContactDaoImpl contactDao = new ContactDaoImpl();
        contactDao.setEntityFactory( new EntityFactory() );
        try {
        	contactDao.setConnection( connectionPool.getConnection( ) );
        	contactDao.getConnection().prepareStatement( "use pm_db" ).executeQuery();
        } catch ( SQLException | PersistentException e ) {
            e.printStackTrace( );
        }
        Contact contact = new Contact();
        Integer workingIdentifier = null;
        contact.setName( "NameContact" );
        contact.setUser( null );
        contact.setType( null );
        try {
            workingIdentifier = contactDao.create( contact );
            contact.setId( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( contact.getId(), contactDao.read( workingIdentifier ).getId() );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        Contact updatedContact = new Contact();
        updatedContact.setId( workingIdentifier );
        updatedContact.setName("UpdateContact");
        updatedContact.setUser(null);
        updatedContact.setType(null);
        try {
            contactDao.update( updatedContact );
        } catch (PersistentException e) {
            Assert.assertTrue( false );
        }
        try {
            Assert.assertEquals( updatedContact, contactDao.read( workingIdentifier ) );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
        	contactDao.delete( workingIdentifier );
        } catch ( PersistentException e ) {
            Assert.assertTrue( false );
        }
        try {
            String sql = " SELECT name,user_id, type_id FROM contact WHERE id = ? ";
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = contactDao.getConnection( ).prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
            preparedStatement.setInt( 1, workingIdentifier );
            resultSet = preparedStatement.executeQuery( );
            Assert.assertFalse( resultSet.next( ) );
        } catch ( SQLException e ) {
            Assert.assertTrue( false );
        }


    }
}

