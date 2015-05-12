package by.vsu.mf.ammc.pm.dao.mysql;

import org.apache.log4j.Logger;

import by.vsu.mf.ammc.pm.domain.Entity;

public class EntityFactory {
	private static Logger logger = Logger.getLogger(EntityFactory.class);

	public <T extends Entity> T create(Class<T> key) {
		try {
			return key.newInstance();
		} catch(InstantiationException | IllegalAccessException e) {
			logger.error("Error creating entity", e);
			return null;
		}
	}
}
