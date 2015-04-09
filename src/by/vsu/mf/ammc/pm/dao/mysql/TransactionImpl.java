package by.vsu.mf.ammc.pm.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.dao.abstraction.Transaction;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TransactionImpl implements Transaction {
	private static Logger logger = Logger.getLogger(TransactionImpl.class);

	private static Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> daos = new ConcurrentHashMap<>();
	static {
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
	public <Type extends Dao<?>> Type getDao(Class<Type> key) throws PersistentException {
		try {
			BaseDaoImpl dao = daos.get(key).newInstance();
			if(dao != null) {
				dao.setConnection(connection);
				dao.setEntityFactory(entityFactory);
			}
			return (Type)dao;
		} catch(InstantiationException | IllegalAccessException e) {
			throw new PersistentException(e);
		}
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
