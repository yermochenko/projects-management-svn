package by.vsu.mf.ammc.pm.dao.abstraction.user;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface UserDao extends Dao<User> {
	public User readByUserGroup(Integer group_id) throws PersistentException;
}
