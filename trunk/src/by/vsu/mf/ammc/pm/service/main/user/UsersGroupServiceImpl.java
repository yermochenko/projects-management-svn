package by.vsu.mf.ammc.pm.service.main.user;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.UsersGroupService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class UsersGroupServiceImpl extends ServiceImpl implements UsersGroupService {
	@Override
	public List<UsersGroup> findAll() throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsersGroup> findPossibleParents(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsersGroup findById(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(UsersGroup group) throws PersistentException {
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
