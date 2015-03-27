package by.vsu.mf.ammc.pm.test.project.specification;

import java.sql.Connection;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.project.specification.UseCaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class UseCaseIdentityMapTest {
	public static void main(String[] args) throws PersistentException, SQLException {
		//TODO Logger
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root", "root", 1, 1, 10000);
		Connection conn = pool.getConnection();
		conn.createStatement().execute("use pm_db");
		
		UseCaseDaoImpl dao = new UseCaseDaoImpl();
		dao.setConnection(conn);
		
		int id = 5001;
		try {
			UseCase useCase = new UseCase();
			useCase.setName("name1");
			Module module = new Module();
			module.setId(5001);
			useCase.setModule(module);
			id = dao.create(useCase);
			System.out.println("create id = " + id);
		} catch(Exception e) {
			System.err.println("create with module");
			e.printStackTrace();
		}
		UseCase useCase = null;
		try {
			useCase = dao.read(id);
			System.out.println("read: name = " + useCase.getName());
		} catch(Exception e) {
			System.err.println("read");
			e.printStackTrace();
		}
		try {
			useCase = dao.read(id);
			System.out.println("read: name = " + useCase.getName());
		} catch(Exception e) {
			System.err.println("read");
			e.printStackTrace();
		}
		
		try {
			if(useCase != null) {
				dao.delete(useCase.getId());
				System.out.println("delete: done");
			}
		} catch(Exception e) {
			System.err.println("delete");
			e.printStackTrace();
		}
	}
}
