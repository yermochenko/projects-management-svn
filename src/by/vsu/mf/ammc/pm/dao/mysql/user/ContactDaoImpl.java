package by.vsu.mf.ammc.pm.dao.mysql.user;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.apache.log4j.Logger;

public class ContactDaoImpl extends BaseDaoImpl implements ContactDao {
	private static Logger logger = Logger.getLogger(ContactDaoImpl.class);
    private Map< Integer, Contact > identityMap = new HashMap< Integer, Contact >( );
	@Override
	public Integer create(Contact contact) throws PersistentException {
		String sql = "INSERT INTO `contact` (`name`, `user_id`, `type_id`) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, contact.getName());
			statement.setInt(2, contact.getId());
			statement.setInt(3, contact.getType().getId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				logger.error("There is no autoincremented index after trying to add record into table `contact`");
				throw new PersistentException();
			}
		} catch(SQLException e) {
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
	public Contact read(Integer identity) throws PersistentException {
		if ( identityMap.containsKey( identity ) ) {
            return identityMap.get( identity );
        }
		String sql = "SELECT `name`, `user_id`, `type_id` FROM `contact` WHERE `identity` = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, identity);
			resultSet = statement.executeQuery();
			Contact contact = null;
			if(resultSet.next()) {
				contact = getEntityFactory().create(Contact.class);
				contact.setId(identity);
				contact.setName(resultSet.getString("name"));
				Integer userIdentity = resultSet.getInt("user_id");
				if(!resultSet.wasNull()) {
					User user = new User();
					user.setId(userIdentity);
					contact.setUser(user);
				}
				Integer typeIdentity = resultSet.getInt("type_id");
				if(!resultSet.wasNull()) {
					ContactsType type = new ContactsType();
					type.setId(typeIdentity);
					contact.setType(type);
				}
			}
			identityMap.put( identity, contact );
			return contact;
		} catch(SQLException e) {
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
	public void update(Contact contact) throws PersistentException {
		String sql = "UPDATE `contact` SET `name` = ?, `user_id` = ?, `type_id` = ? WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, contact.getName());
			if(contact.getUser() != null && contact.getUser().getId() != null) {
				statement.setInt(2, contact.getUser().getId());
			} else {
				statement.setNull(2, Types.INTEGER);
			}
			if(contact.getType() != null && contact.getType().getId() != null) {
				statement.setInt(3, contact.getType().getId());
			} else {
				statement.setNull(3, Types.INTEGER);
			}
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}
	
	@Override
	public void delete(Integer identity) throws PersistentException {
		String sql = "DELETE FROM `contact` WHERE `id` = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setInt(1, identity);
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				statement.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
