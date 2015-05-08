package by.vsu.mf.ammc.pm.service.abstraction.project;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface ProjectService extends Service {
	List<Project> findByProjectsCategory(ProjectsCategory category) throws PersistentException;

	Project findById(int id) throws PersistentException;

	void save(Project project) throws PersistentException;

	boolean canDelete(int id) throws PersistentException;

	void delete(int id) throws PersistentException;
}
