package by.vsu.mf.ammc.pm.service.main.user;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.UserService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class UserServiceImpl extends ServiceImpl implements UserService {
	@Override
	public List<User> findAll() throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByUsersGroup(UsersGroup group) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(int id) throws PersistentException {
		return this.getTransaction().getDao(UserDao.class).read(id);
	}

	@Override
	public User findByLoginAndPassword(String login, String password) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(User user) throws PersistentException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(int id) throws PersistentException {
		// TODO Auto-generated method stub
	}
}
