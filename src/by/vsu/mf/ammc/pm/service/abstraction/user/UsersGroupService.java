package by.vsu.mf.ammc.pm.service.abstraction.user;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface UsersGroupService extends Service {
	List<UsersGroup> findAll() throws PersistentException;

	List<UsersGroup> findPossibleParents(int id) throws PersistentException;

	UsersGroup findById(int id) throws PersistentException;

	void save(UsersGroup group) throws PersistentException;

	boolean canDelete(int id) throws PersistentException;

	void delete(int id) throws PersistentException;
}
