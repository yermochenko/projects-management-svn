package by.vsu.mf.ammc.pm.service.main.project.management;

import java.util.List;
import java.util.Map;

import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.management.EmployeeService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class EmployeeServiceImpl extends ServiceImpl implements EmployeeService {
	@Override
	public Map<Project, List<EmployeesRole>> findByUser(User user) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}
}
