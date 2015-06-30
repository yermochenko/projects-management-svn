package by.vsu.mf.ammc.pm.dao.abstraction.user;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ContactDao extends Dao<Contact> {
	public boolean canDelete(int id) throws PersistentException;

	public List<Contact> getContactsByUser(User user) throws PersistentException;
}
