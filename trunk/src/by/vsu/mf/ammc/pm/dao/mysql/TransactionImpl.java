package by.vsu.mf.ammc.pm.dao.mysql;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.dao.abstraction.Transaction;
import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.EmployeeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactsTypeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UsersGroupDao;
import by.vsu.mf.ammc.pm.dao.mysql.project.ProjectDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.EmployeeDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.UsersGroupDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
	private static Logger logger = Logger.getLogger(TransactionImpl.class);

	private static Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> daos = new ConcurrentHashMap<>();
	static {
		daos.put( EmployeeDao.class, EmployeeDaoImpl.class );
		daos.put( ProjectDao.class, ProjectDaoImpl.class);
		daos.put( UserDao.class, UserDaoImpl.class);
		daos.put( UsersGroupDao.class, UsersGroupDaoImpl.class);
		daos.put( ContactDao.class, ContactDaoImpl.class);
		daos.put( ContactsTypeDao.class, ContactsTypeDaoImpl.class);
	}

	private Connection connection;
	private EntityFactory entityFactory;

	public TransactionImpl() throws PersistentException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			connection.setAutoCommit(false);
			entityFactory = new EntityFactory();
		} catch(SQLException e) {
			logger.error("It is impossible to turn off autocommiting for database connection", e);
			throw new PersistentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Dao<?>> T getDao(Class<T> dao) throws PersistentException {
		Class<? extends BaseDaoImpl> daoImplClass = daos.get(dao);
		if(daoImplClass != null) {
			try {
				BaseDaoImpl daoImpl = daoImplClass.newInstance();
				if(daoImpl != null) {
					daoImpl.setConnection(connection);
					daoImpl.setEntityFactory(entityFactory);
				}
				return (T)daoImpl;
			} catch(InstantiationException | IllegalAccessException e) {
				logger.error("It is impossible to instantiate data access object class", e);
				throw new PersistentException(e);
			}
		}
		return null;
	}

	@Override
	public void commit() throws PersistentException {
		try {
			connection.commit();
		} catch(SQLException e) {
			logger.error("It is impossible to commit transaction", e);
			throw new PersistentException(e);
		}
	}

	@Override
	public void rollback() throws PersistentException {
		try {
			connection.rollback();
		} catch(SQLException e) {
			logger.error("It is impossible to rollback transaction", e);
			throw new PersistentException(e);
		}
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch(SQLException e) {}
	}
}
