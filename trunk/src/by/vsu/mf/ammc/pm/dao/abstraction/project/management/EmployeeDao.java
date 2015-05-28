package by.vsu.mf.ammc.pm.dao.abstraction.project.management;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.project.Project;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.EmployeesRole;
import by.vsu.mf.ammc.pm.domain.user.User;

import java.util.List;
import java.util.Map;

public interface EmployeeDao extends Dao< Employee > {
    Map< Project, List< EmployeesRole > > readByUser( User user );
}
