package by.vsu.mf.ammc.pm.service.main.project;

import java.util.List;

import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.ProjectsCategoryService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ProjectsCategoryServiceImpl extends ServiceImpl implements ProjectsCategoryService {
	@Override
	public List<ProjectsCategory> findAll() throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectsCategory> findPossibleParents(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectsCategory findById(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ProjectsCategory category) throws PersistentException {
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
