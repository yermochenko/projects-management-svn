package by.vsu.mf.ammc.pm.dao.abstraction.user;

import java.util.List;
import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ContactsTypeDao extends Dao<ContactsType> {
	List<ContactsType> readAll() throws PersistentException;
}
