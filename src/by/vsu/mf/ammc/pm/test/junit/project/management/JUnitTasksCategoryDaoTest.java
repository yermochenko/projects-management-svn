package by.vsu.mf.ammc.pm.test.junit.project.management;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TasksCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class JUnitTasksCategoryDaoTest {
    private TasksCategoryDaoImpl dao = null;
    private TasksCategoryDaoImpl createDao(Connection connection) {
        TasksCategoryDaoImpl dao = new TasksCategoryDaoImpl();
        dao.setConnection(connection);
        dao.setEntityFactory(new EntityFactory());
        return dao;
    }
    @Before
    public void initTest(){
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pm_db", "pm_user", "pm_password", 5, 5, 0);
        } catch(PersistentException e) {
            e.printStackTrace();
        }
        try {
            dao = createDao(pool.getConnection());
        } catch(PersistentException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCRUD(){
        //create
        TasksCategory tasksCategory1 = dao.getEntityFactory().create(TasksCategory.class);
        tasksCategory1.setName("test1");
        Integer id1 = null;
        try {
            id1 = dao.create(tasksCategory1);
            tasksCategory1.setId(id1);
        } catch (PersistentException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        Assert.assertNotNull("was created", tasksCategory1);

        //read
        TasksCategory tasksCategory1Read = null;
        try {
            tasksCategory1Read = dao.read(id1);
        } catch (PersistentException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        Assert.assertNotNull("was read", tasksCategory1Read);
        Assert.assertEquals(tasksCategory1.getId(), tasksCategory1Read.getId());
        Assert.assertEquals(tasksCategory1.getName(), tasksCategory1Read.getName());
        Assert.assertEquals(tasksCategory1.getParent(), tasksCategory1Read.getParent());

        //create with parent
        TasksCategory tasksCategory2 = dao.getEntityFactory().create(TasksCategory.class);
        tasksCategory2.setName("test2");
        tasksCategory2.setParent(tasksCategory1);
        Integer id2 = null;
        try {
            id2 = dao.create(tasksCategory2);
            tasksCategory2.setId(id2);
        } catch (PersistentException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        Assert.assertNotNull("was created", tasksCategory2);

        //update
        tasksCategory1.setName("newTest1");
        try {
            dao.update(tasksCategory1);
        } catch (PersistentException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        Assert.assertNotNull("was updated", tasksCategory1);

        //delete
        try {
            dao.delete(id2);
            tasksCategory2 = dao.read(id2);
        } catch (PersistentException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        Assert.assertNull("was delete", tasksCategory2);
    }
}
