package by.vsu.mf.ammc.pm.test.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TaskDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskIdentityMapTest {
    public static void main( String[] args ) throws PersistentException, SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance( );
        try {
            connectionPool.init( "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306", "root", "root", 1, 20, 10000 );
        } catch ( PersistentException e ) {
            e.printStackTrace( );
        }
        System.out.println( "Identity Map Test...\n" );

        long timeForFirstCircle = System.nanoTime( );
        PreparedStatement statement = null;
        Connection connection = ConnectionPool.getInstance( ).getConnection( );
        connection.prepareStatement( "use pm_db" ).executeQuery( );
        String sqlString = "Select name, description, plan_time, difficulty, open_date, accept_date, close_date, category_id, requirement_id,module_id,employee_id, status from task where id = ?";
        for ( int i = 0 ; i < 10000 ; i++ ) {
            try {
                statement = connection.prepareStatement( sqlString, Statement.RETURN_GENERATED_KEYS );
                statement.setInt( 1, 1 );
                statement.executeQuery( );
            } catch ( SQLException e ) {
                System.out.println( "Some troubles =/" );
            }
        }
        timeForFirstCircle = System.nanoTime( ) - timeForFirstCircle;
        System.out.println( "Results of first circle.." + timeForFirstCircle );

        long timeForSecondCircle = System.nanoTime( );
        TaskDaoImpl taskDaoImpl = new TaskDaoImpl( );
        taskDaoImpl.setEntityFactory( new EntityFactory( ) );
        try {
            taskDaoImpl.setConnection( connectionPool.getConnection( ) );
            taskDaoImpl.getConnection( ).prepareStatement( "use pm_db" ).executeQuery( );
        } catch ( SQLException e ) {
            e.printStackTrace( );
        }
        for ( int i = 0 ; i < 10000 ; i++ ) {
            try {
                taskDaoImpl.read( 1 );
            } catch ( PersistentException e ) {
                System.out.println( "Some troubles =/" );
            }
        }
        timeForSecondCircle = System.nanoTime( ) - timeForSecondCircle;
        System.out.println( "Results of first circle.." + timeForSecondCircle );
        System.out.println( "Difference.....seconds.." );
        System.out.println( ( float ) ( timeForFirstCircle - timeForSecondCircle ) / 1000000000 );
    }
}
