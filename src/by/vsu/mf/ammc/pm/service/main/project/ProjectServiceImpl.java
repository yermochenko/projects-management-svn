package by.vsu.mf.ammc.pm.service.main.project;

import java.util.ArrayList;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ModuleDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectDao;
import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TeamDao;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
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
		ProjectDao pd = getTransaction().getDao(ProjectDao.class);
		Project project = pd.read(id);
		if (project == null) {
			return false;
		} else {
			ModuleDao md = getTransaction().getDao(ModuleDao.class);
			ArrayList<Module> modules = md.read(project);
			if (!modules.isEmpty()) {
				return false;
			}
			TeamDao td = getTransaction().getDao(TeamDao.class);
			ArrayList<Team> teams = td.read(project);
			if (!teams.isEmpty()) {
				return false;
			}
			return true;
		}
	}

	@Override
	public void delete(int id) throws PersistentException {
		ProjectDao pd = getTransaction().getDao(ProjectDao.class);
		if (canDelete(id)) {
			/*ProjectsCategoryServiceImpl pcsi = new ProjectsCategoryServiceImpl();
			List<ProjectsCategory> pcs = pcsi.findAll();
			for (ProjectsCategory pc : pcs) {
				List<Project> ps = pc.getProjects();
				int max = ps.size();
				for (int i=0; i<max; i++) {
					Project p = ps.get(i);
					if (p.getId() == id) {
						ps.remove(i);
						i--;
						max--;
					}
				}
			}*/
			pd.delete(id);
		}
	}

	@Override
	public List<Project> findByProjectsCategory(Integer category_id) throws PersistentException {
		
		return this.getTransaction().getDao(ProjectDao.class).readByProjectsCategory(category_id);

	}
}
