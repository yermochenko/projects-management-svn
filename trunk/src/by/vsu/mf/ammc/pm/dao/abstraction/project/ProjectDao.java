package by.vsu.mf.ammc.pm.dao.abstraction.project;

import java.sql.SQLException;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ProjectDao extends Dao<Project> {
	
	boolean projectByUserIsEmpty(Integer user_id) throws SQLException;
	
	List<Project> readByProjectsCategory(Integer category_id) throws PersistentException;
}
