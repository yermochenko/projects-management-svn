package by.vsu.mf.ammc.pm.service.main.user;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactsTypeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UsersGroupDao;
import by.vsu.mf.ammc.pm.dao.mysql.user.UsersGroupDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.UsersGroupService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

import java.util.List;

public class UsersGroupServiceImpl extends ServiceImpl implements UsersGroupService {
	
	@Override
	public List<UsersGroup> findAll() throws PersistentException {
		UsersGroupDao usersGroupDao = getTransaction().getDao( UsersGroupDao.class );
		return usersGroupDao.readAll();
	}

	@Override
	public List<UsersGroup> findPossibleParents(int id) throws PersistentException {
		return this.getTransaction().getDao(UsersGroupDaoImpl.class).readPossibleParents(id);
	}

	@Override
	public UsersGroup findById(int id) throws PersistentException {
		return this.getTransaction().getDao(UsersGroupDao.class).read(id);
	}

	@Override
	public void save(UsersGroup group) throws PersistentException {
		UsersGroupDao usersGroupDao = getTransaction().getDao(UsersGroupDao.class);
		Integer id = group.getId();
		if(id != null){
			usersGroupDao.update(group);
		}else{
			group.setId(usersGroupDao.create(group));
		}	
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		UsersGroup usersGroup=this.getTransaction().getDao(UsersGroupDao.class).read(id);
		return usersGroup != null && usersGroup.getUsers().isEmpty() && usersGroup.getChildren().isEmpty();
	}

	@Override
	public void delete(int id) throws PersistentException {
        //persistentException will be looked higher
        this.getTransaction().getDao(UsersGroupDao.class).delete(id);
	}
}
