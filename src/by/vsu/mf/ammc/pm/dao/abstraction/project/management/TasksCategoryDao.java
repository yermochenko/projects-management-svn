package by.vsu.mf.ammc.pm.dao.abstraction.project.management;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface TasksCategoryDao extends Dao<TasksCategory> {
	List<TasksCategory> readAll() throws PersistentException;
}
