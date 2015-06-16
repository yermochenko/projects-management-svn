package by.vsu.mf.ammc.pm.service.main.user;

import java.sql.SQLException;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.EmployeeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TeamDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.dao.mysql.user.UserDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.UserService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class UserServiceImpl extends ServiceImpl implements UserService {
	@Override
	public List<User> findAll() throws PersistentException {
			UserDao userDao = getTransaction().getDao( UserDao.class );
			return userDao.readAll();
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
	public boolean canDelete(int id) throws PersistentException, SQLException {
		boolean emptyEmployee = this.getTransaction().getDao(EmployeeDao.class).employeeByUserIsEmpty(id);
		boolean emptyTeam = this.getTransaction().getDao(TeamDao.class).teamByUserIsEmpty(id);
		boolean emptyProject = this.getTransaction().getDao(ProjectDao.class).projectByUserIsEmpty(id);

		if(emptyEmployee && emptyTeam && emptyProject) return true;
		else return false;
	}

	@Override
	public void delete(int id) throws PersistentException {
		try {
			if(canDelete(id)) {
				this.getTransaction().getDao(UserDao.class).delete(id);
			}
		} catch(PersistentException e) {
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User findByUsersGroup(int group_id) throws PersistentException {
		
		return this.getTransaction().getDao(UserDaoImpl.class).readByUserGroup(group_id);
	}
}
