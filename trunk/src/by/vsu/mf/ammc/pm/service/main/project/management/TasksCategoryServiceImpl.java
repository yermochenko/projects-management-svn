package by.vsu.mf.ammc.pm.service.main.project.management;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.management.TasksCategoryService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class TasksCategoryServiceImpl extends ServiceImpl implements TasksCategoryService {
	@Override
	public List<TasksCategory> findAll() throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TasksCategory> findPossibleParents(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TasksCategory findById(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TasksCategory category) throws PersistentException {
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
