package by.vsu.mf.ammc.pm.dao.abstraction.project;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ProjectsCategoryDao extends Dao<ProjectsCategory> {
	List<ProjectsCategory> readAll() throws PersistentException;

	List<ProjectsCategory> readChildsByProjectsCategory(ProjectsCategory projectsCategory) throws PersistentException;	
}
