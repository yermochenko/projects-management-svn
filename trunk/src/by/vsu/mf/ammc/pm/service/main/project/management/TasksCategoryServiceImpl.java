package by.vsu.mf.ammc.pm.service.main.project.management;

import java.util.ArrayList;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TaskDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TasksCategoryDao;
import by.vsu.mf.ammc.pm.dao.mysql.project.management.TasksCategoryDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.management.TasksCategoryService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class TasksCategoryServiceImpl extends ServiceImpl implements TasksCategoryService {
	@Override
	public List<TasksCategory> findAll() throws PersistentException {
		this.getTransaction().getDao(TasksCategoryDao.class).readAll();
		return null;
	}

	@Override
	public List<TasksCategory> findPossibleParents(int id) throws PersistentException {
		return this.getTransaction().getDao(TasksCategoryDaoImpl.class).findPossibleParents(id);
	}

	@Override
	public TasksCategory findById(int id) throws PersistentException {
		TasksCategoryDao tasksCategoryDao = getTransaction().getDao(TasksCategoryDao.class);
		return tasksCategoryDao.read(id);
	}

	@Override
	public void save(TasksCategory category) throws PersistentException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		TasksCategoryDao tcd = getTransaction().getDao(TasksCategoryDao.class);
		TasksCategory tc = tcd.read(id);
		if (tc == null)
		{
			return false;
		}
		else
		{
			if (!tc.getChildren().isEmpty())
			{
				return false;
			}
			else
			{
				TaskDao td = getTransaction().getDao(TaskDao.class);
				ArrayList<Task> tasks = td.read(tc);
				if (!tasks.isEmpty())
				{
					return false;
				}
				return true;
			}
		}
	}

	@Override
	public void delete(int id) throws PersistentException {
		TasksCategoryDao tasksCategoryDao = getTransaction().getDao(TasksCategoryDao.class);
		tasksCategoryDao.delete(id);
	}
}
