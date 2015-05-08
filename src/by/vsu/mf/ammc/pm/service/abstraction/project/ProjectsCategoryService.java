package by.vsu.mf.ammc.pm.service.abstraction.project;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface ProjectsCategoryService extends Service {
	List<ProjectsCategory> findAll() throws PersistentException;

	List<ProjectsCategory> findPossibleParents(int id) throws PersistentException;

	ProjectsCategory findById(int id) throws PersistentException;

	void save(ProjectsCategory category) throws PersistentException;

	boolean canDelete(int id) throws PersistentException;

	void delete(int id) throws PersistentException;
}
