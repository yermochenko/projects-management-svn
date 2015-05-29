package by.vsu.mf.ammc.pm.dao.abstraction.project;

import java.util.ArrayList;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public interface ModuleDao extends Dao<Module> {
	ArrayList<Module> read(Project project) throws PersistentException;
}
