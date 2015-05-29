package by.vsu.mf.ammc.pm.service.main.project.management;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.EmployeeDao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.project.management.EmployeeService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl extends ServiceImpl implements EmployeeService {
    @Override
    public Map< Project, List< EmployeesRole > > findByUser( User user ) throws PersistentException {
        EmployeeDao employeeDao = getTransaction( ).getDao( EmployeeDao.class );
        return employeeDao.readByUser( user );
    }
}
