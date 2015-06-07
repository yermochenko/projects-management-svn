package by.vsu.mf.ammc.pm.dao.abstraction.project.management;

import java.sql.SQLException;
import java.util.ArrayList;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Team;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface TeamDao extends Dao<Team> {
	ArrayList<Team> read(Project project) throws PersistentException;
	
	boolean teamByUserIsEmpty(Integer user_id) throws SQLException;
	
}
