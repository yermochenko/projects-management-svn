package by.vsu.mf.ammc.pm.dao.abstraction;

import by.vsu.mf.ammc.pm.domain.Entity;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface Dao<T extends Entity> {
	Integer create(T entity) throws PersistentException;

	T read(Integer id) throws PersistentException;

	void update(T entity) throws PersistentException;

	void delete(Integer id) throws PersistentException;
}
