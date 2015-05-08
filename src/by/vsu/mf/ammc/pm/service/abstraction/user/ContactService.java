package by.vsu.mf.ammc.pm.service.abstraction.user;

import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface ContactService extends Service {
	void save(Contact contact) throws PersistentException;

	void delete(int id) throws PersistentException;
}
