package by.vsu.mf.ammc.pm.dao.mysql;

import java.sql.Connection;

abstract public class BaseDaoImpl {
	private Connection connection;
	private EntityFactory entityFactory;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public EntityFactory getEntityFactory() {
		return entityFactory;
	}

	public void setEntityFactory(EntityFactory entityFactory) {
		this.entityFactory = entityFactory;
	}
}
