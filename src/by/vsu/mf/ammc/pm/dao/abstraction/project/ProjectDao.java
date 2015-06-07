package by.vsu.mf.ammc.pm.dao.abstraction.project;

import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.Project;

public interface ProjectDao extends Dao<Project> {
	
	boolean projectByUserIsEmpty(Integer user_id) throws SQLException;
	
}
