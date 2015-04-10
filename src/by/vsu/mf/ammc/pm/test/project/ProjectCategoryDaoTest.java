package by.vsu.mf.ammc.pm.test.project;

import java.sql.Connection;
import java.sql.SQLException;

import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.dao.mysql.project.ProjectsCategoryDaoImpl;
import by.vsu.mf.ammc.pm.dao.util.pool.ConnectionPool;
import by.vsu.mf.ammc.pm.domain.project.ProjectsCategory;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class ProjectCategoryDaoTest {
	public static void main(String[] args) throws PersistentException, SQLException, ClassNotFoundException {

		ConnectionPool pool = ConnectionPool.getInstance();
		pool.init("com.mysql.jdbc.Driver", "jdbc:mysql://localhost", "root","root", 1, 1, 10000);
		
		ProjectsCategoryDaoImpl pcdao = new ProjectsCategoryDaoImpl();
		pcdao.setEntityFactory(new EntityFactory());
		Connection connection = pool.getConnection();
		connection.createStatement().execute("use pm_db");
		pcdao.setConnection(connection);

		// create
		ProjectsCategory create_project_category = new ProjectsCategory();
		int create_id = -1;
		create_project_category.setName("test");
		ProjectsCategory pc = new ProjectsCategory();
		pc.setId(7203);
		create_project_category.setParent(pc);
		create_id = pcdao.create(create_project_category);
		assert create_id != -1 : "Failed on create";
		
		
		// read
		ProjectsCategory read_project_category = new ProjectsCategory();
		assert create_project_category.getName() == read_project_category.getName() && create_project_category.getParent()==read_project_category.getParent() :  "Failed on read";
		
		// update
		ProjectsCategory update_project_category = new ProjectsCategory();
		create_project_category.setName("test-test-test");
		pc.setId(7201);
		update_project_category.setId(create_id);
		update_project_category.setName(create_project_category.getName());
		update_project_category.setParent(pc);
		pcdao.update(update_project_category);
		read_project_category = pcdao.read(create_id);
		assert update_project_category.getName() == read_project_category.getName() && update_project_category.getParent() == read_project_category.getParent() : "Failed update";
		
		// delete
		pcdao.delete(create_id);
		assert pcdao.read(create_id) == null : "Failed on delete";
	}
}
