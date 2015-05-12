package by.vsu.mf.ammc.pm.dao.abstraction;

import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface Transaction {
	<T extends Dao<?>> T getDao(Class<T> dao) throws PersistentException;

	void commit() throws PersistentException;

	void rollback() throws PersistentException;

	void close();
}
