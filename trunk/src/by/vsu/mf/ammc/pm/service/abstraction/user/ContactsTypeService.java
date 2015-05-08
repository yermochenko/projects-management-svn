package by.vsu.mf.ammc.pm.service.abstraction.user;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface ContactsTypeService extends Service {
	List<ContactsType> findAll() throws PersistentException;

	ContactsType findById(int id) throws PersistentException;

	void save(ContactsType type) throws PersistentException;

	boolean canDelete(int id) throws PersistentException;

	void delete(int id) throws PersistentException;
}
