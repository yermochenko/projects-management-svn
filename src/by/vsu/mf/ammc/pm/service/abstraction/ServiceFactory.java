package by.vsu.mf.ammc.pm.service.abstraction;

import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ServiceFactory {
	<T extends Service> T getService(Class<T> service) throws PersistentException;

	void close();
}
