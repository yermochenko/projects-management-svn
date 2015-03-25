package by.vsu.mf.ammc.pm.dao.mysql.project.management;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import by.vsu.mf.ammc.pm.dao.abstraction.project.management.TaskDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.management.Employee;
import by.vsu.mf.ammc.pm.domain.project.management.Task;
import by.vsu.mf.ammc.pm.domain.project.management.TasksCategory;
import by.vsu.mf.ammc.pm.domain.project.management.TasksStatus;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.exception.PersistentException;

public class TaskDaoImpl extends BaseDaoImpl implements TaskDao {

	@Override
	public Integer create(Task task) throws PersistentException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlString = "INSERT INTO task (name, description, plan_time, difficulty, open_date, accept_date, close_date, category_id, requirement_id,module_id,employee_id, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, task.getName());
			statement.setString(2, task.getDescription());
			statement.setInt(3, task.getPlanTime());
			statement.setFloat(4, task.getDifficulty());
			statement.setDate(5, new Date(task.getOpenDate().getTime()));
			if(task.getAcceptDate() != null) {
				statement.setDate(6, new Date(task.getAcceptDate().getTime()));
			} else {
				statement.setNull(6, Types.DATE);
			}
			if(task.getCloseDate() != null) {
				statement.setDate(7, new Date(task.getCloseDate().getTime()));
			} else {
				statement.setNull(7, Types.DATE);
			}
			if(task.getCategory() != null && task.getCategory().getId() != null) {
				statement.setInt(8, task.getCategory().getId());
			} else {
				statement.setNull(8, Types.INTEGER);
			}

			if(task.getRequirement() != null && task.getRequirement().getId() != null) {
				statement.setInt(9, task.getRequirement().getId());
			} else {
				statement.setNull(9, Types.INTEGER);
			}
			if(task.getModule() != null && task.getModule().getId() != null) {
				statement.setInt(10, task.getModule().getId());
			} else {
				statement.setNull(10, Types.INTEGER);
			}

			if(task.getEmployee() != null && task.getEmployee().getId() != null) {
				statement.setInt(11, task.getEmployee().getId());
			} else {
				statement.setNull(11, Types.INTEGER);
			}

			if(task.getStatus() != null) {
				statement.setInt(12, task.getStatus().ordinal());
			} else {
				statement.setNull(12, Types.INTEGER);
			}
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			Integer id = null;
			if(resultSet.next()) {
				id = resultSet.getInt(1);
			} else {
				throw new PersistentException();
			}
			return id;

		} catch(SQLException e) {
			// TODO logger.error
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public Task read(Integer id) throws PersistentException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlString = "Select name, description, plan_time, difficulty, open_date, accept_date, close_date, category_id, requirement_id,module_id,employee_id, status from task where id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			Task task = null;
			if(resultSet.next()) {
				task = new Task();
				task.setId(id);
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("description"));
				task.setPlanTime(resultSet.getInt("plan_time"));
				task.setDifficulty(resultSet.getFloat("difficulty"));
				task.setOpenDate(new Date(resultSet.getDate("open_date").getTime()));
				if(resultSet.getDate("accept_date") != null) {
					Date acceptDate = new Date(resultSet.getDate("accept_date").getTime());
					if(!resultSet.wasNull()) {
						task.setAcceptDate(acceptDate);
					}
				}
				if(resultSet.getDate("close_date") != null) {
					Date closeDate = new Date(resultSet.getDate("close_date").getTime());
					if(!resultSet.wasNull()) {
						task.setCloseDate(closeDate);
					}
				}
				TasksCategory tasksCategory = new TasksCategory();
				tasksCategory.setId(resultSet.getInt("category_id"));
				if(!resultSet.wasNull()) {
					task.setCategory(tasksCategory);
				}
				Requirement requirement = new Requirement();
				requirement.setId(resultSet.getInt("requirement_id"));
				if(!resultSet.wasNull()) {
					task.setRequirement(requirement);
				}
				Module module = new Module();
				module.setId(resultSet.getInt("module_id"));
				if(!resultSet.wasNull()) {
					task.setModule(module);
				}
				Employee employee = new Employee();
				employee.setId(resultSet.getInt("employee_id"));
				if(!resultSet.wasNull()) {
					task.setEmployee(employee);
				}

				switch(resultSet.getInt("status")) {
					case 0:
						task.setStatus(TasksStatus.NEW);
						break;
					case 1:
						task.setStatus(TasksStatus.ACCEPTED);
						break;
					case 2:
						task.setStatus(TasksStatus.STARTED);
						break;
					case 3:
						task.setStatus(TasksStatus.RENEW);
						break;
					case 4:
						task.setStatus(TasksStatus.DONE);
						break;
					case 5:
						task.setStatus(TasksStatus.APPROVED);
						break;
					default:
						break;
				}
			}

			return task;

		} catch(SQLException e) {
			// TODO: logger.error
			throw new PersistentException(e);
		} finally {
			try {
				resultSet.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public void update(Task task) throws PersistentException {
		PreparedStatement statement = null;

		try {
			String sqlString = "UPDATE task set name = ?, description = ?, plan_time = ?, difficulty = ?, open_date = ?, accept_date = ?, close_date = ?, category_id = ?, requirement_id = ?, module_id = ?, employee_id = ?, status = ? WHERE id = ? ";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, task.getName());
			statement.setString(2, task.getDescription());
			statement.setInt(3, task.getPlanTime());
			statement.setFloat(4, task.getDifficulty());
			statement.setDate(5, new Date(task.getOpenDate().getTime()));

			if(task.getAcceptDate() != null) {
				statement.setDate(6, new Date(task.getAcceptDate().getTime()));
			} else {
				statement.setNull(6, Types.DATE);
			}
			if(task.getCloseDate() != null) {
				statement.setDate(7, new Date(task.getCloseDate().getTime()));
			} else {
				statement.setNull(7, Types.DATE);
			}

			if(task.getCategory() != null && task.getCategory().getId() != null) {
				statement.setInt(8, task.getCategory().getId());
			} else {
				statement.setNull(8, Types.INTEGER);
			}

			if(task.getRequirement() != null && task.getRequirement().getId() != null) {
				statement.setInt(9, task.getRequirement().getId());
			} else {
				statement.setNull(9, Types.INTEGER);
			}
			if(task.getModule() != null && task.getModule().getId() != null) {
				statement.setInt(10, task.getModule().getId());
			} else {
				statement.setNull(10, Types.INTEGER);
			}

			if(task.getEmployee() != null && task.getEmployee().getId() != null) {
				statement.setInt(11, task.getEmployee().getId());
			} else {
				statement.setNull(11, Types.INTEGER);
			}

			if(task.getStatus() != null) {
				statement.setInt(12, task.getStatus().ordinal());
			} else {
				statement.setNull(12, Types.INTEGER);
			}

			statement.setInt(13, task.getId());
			statement.executeUpdate();
		} catch(SQLException e) {
			// TODO logger.error
			throw new PersistentException();
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}

	}

	@Override
	public void delete(Integer id) throws PersistentException {
		PreparedStatement statement = null;
		try {
			String sqlString = "DELETE from task WHERE id = ?";
			statement = getConnection().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException e) {
			// TODO logger.error
			throw new PersistentException();
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}

	}

}
