package by.vsu.mf.ammc.pm.service.abstraction.project.management;

import java.util.List;
import java.util.Map;

import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.Service;

public interface EmployeeService extends Service {
	Map<Project, List<EmployeesRole>> findByUser(User user) throws PersistentException;
}
