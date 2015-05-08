package by.vsu.mf.ammc.pm.service.abstraction.project.management;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface TasksCategoryService extends Service {
	List<TasksCategory> findAll() throws PersistentException;

	List<TasksCategory> findPossibleParents(int id) throws PersistentException;

	TasksCategory findById(int id) throws PersistentException;

	void save(TasksCategory category) throws PersistentException;

	boolean canDelete(int id) throws PersistentException;

	void delete(int id) throws PersistentException;
}
