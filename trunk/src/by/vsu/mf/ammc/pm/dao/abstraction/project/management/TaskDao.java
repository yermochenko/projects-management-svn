package by.vsu.mf.ammc.pm.dao.abstraction.project.management;

import java.util.ArrayList;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface TaskDao extends Dao<Task> {
	ArrayList<Task> read(TasksCategory tasksCategory) throws PersistentException;
}
