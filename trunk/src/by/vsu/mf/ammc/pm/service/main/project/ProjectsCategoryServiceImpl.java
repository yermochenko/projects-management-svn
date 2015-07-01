package by.vsu.mf.ammc.pm.service.main.project;

import java.util.ArrayList;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.project.ProjectsCategoryDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UsersGroupDao;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.ProjectsCategoryService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ProjectsCategoryServiceImpl extends ServiceImpl implements ProjectsCategoryService {
	@Override
	public List<ProjectsCategory> findAll() throws PersistentException {
		return this.getTransaction().getDao(ProjectsCategoryDao.class).readAll();
	}

	@Override
	public List<ProjectsCategory> findPossibleParents(int id) throws PersistentException {
		ProjectsCategoryDao projectsCategoryDao = getTransaction().getDao(ProjectsCategoryDao.class);
		ProjectsCategory projectsCategory = projectsCategoryDao.read(id);
		List<ProjectsCategory> list = new  ArrayList<ProjectsCategory>();
		List<ProjectsCategory> tempList = new  ArrayList<ProjectsCategory>();
		
		List<ProjectsCategory> temp = new  ArrayList<ProjectsCategory>();
		
		tempList = projectsCategoryDao.readChildsByProjectsCategory(projectsCategory);
		while(tempList.size()>0){
			list.addAll(tempList);
			for(ProjectsCategory pr: tempList){
				temp.addAll(projectsCategoryDao.readChildsByProjectsCategory(pr));
			}
			tempList.clear();
			tempList.addAll(temp);
			temp.clear();
		}
		
		return list;
	}

	@Override
	public ProjectsCategory findById(int id) throws PersistentException {
		return this.getTransaction().getDao(ProjectsCategoryDao.class).read(id);
	}

	@Override
	public void save(ProjectsCategory category) throws PersistentException {
		ProjectsCategoryDao projectsCategoryDao = getTransaction().getDao(ProjectsCategoryDao.class);
		Integer id = category.getId();
		if(id != null){
			projectsCategoryDao.update(category);
		}else{
			category.setId(projectsCategoryDao.create(category));
		}	
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		ProjectsCategory pc = this.getTransaction().getDao(ProjectsCategoryDao.class).read(id);
		return pc != null && pc.getChildren().isEmpty() && pc.getProjects().isEmpty();
	}

	@Override
	public void delete(int id) throws PersistentException {
        this.getTransaction().getDao(ProjectsCategoryDao.class).delete(id);
	}
}
