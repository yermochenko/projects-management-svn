package by.vsu.mf.ammc.pm.service.main.project;

import java.util.List;
import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectDao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.ProjectService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ProjectServiceImpl extends ServiceImpl implements ProjectService {
	@Override
	public List<Project> findByProjectsCategory(ProjectsCategory category) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findById(int id) throws PersistentException {
		return this.getTransaction().getDao(ProjectDao.class).read(id);
	}

	@Override
	public void save(Project project) throws PersistentException {
		ProjectDao projectDao = getTransaction().getDao(ProjectDao.class);
		Integer id = project.getId();
		if(id != null) {
			projectDao.update(project);
		} else {
			project.setId(projectDao.create(project));
		}
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
