package by.vsu.mf.ammc.pm.dao.abstraction;

import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface Transaction {
	<Type extends Dao<?>> Type getDao(Class<Type> dao) throws PersistentException;

	void commit() throws PersistentException;

	void rollback() throws PersistentException;

	void close();
}
