package by.vsu.mf.ammc.pm.service.abstraction.user;

import java.sql.SQLException;
import java.util.List;

import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface UserService extends Service {
	List<User> findAll() throws PersistentException;

	List<User> findByUsersGroup(UsersGroup group) throws PersistentException;

	User findById(int id) throws PersistentException;

	User findByLoginAndPassword(String login, String password) throws PersistentException;

	void save(User user) throws PersistentException;

	boolean canDelete(int id) throws PersistentException, SQLException;

	void delete(int id) throws PersistentException, SQLException;
}
