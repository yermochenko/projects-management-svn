package by.vsu.mf.ammc.pm.service.abstraction;

import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ServiceFactory {
	<Type extends Service> Type getService(Class<Type> service) throws PersistentException;

	void close();
}
