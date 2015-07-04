package by.vsu.mf.ammc.pm.dao.mysql.user;

import by.vsu.mf.ammc.pm.dao.abstraction.user.UsersGroupDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersGroupDaoImpl extends BaseDaoImpl implements UsersGroupDao {
	private static Logger logger = Logger.getLogger( UsersGroupDaoImpl.class );
	private Map< Integer, UsersGroup > cacheMap = new HashMap<>( );
	
	public Integer create(UsersGroup entity) throws PersistentException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO `users_group` (`name`, `parent_id`) VALUES (?, ?)";
			ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, entity.getName());
			
			UsersGroup parent = entity.getParent();
			if(parent != null && parent.getId() != null){
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			Integer id = null;
			if(rs.next()){
				id = rs.getInt(1);
			} else {
				// TODO logger.error
				throw new PersistentException();
			}
			return id;
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				rs.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				ps.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public UsersGroup read(Integer id) throws PersistentException {
        if ( cacheMap.containsKey( id ) ) {
            return cacheMap.get( id );
        }		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT `name`, `parent_id` FROM `users_group` WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			UsersGroup ug = null;
			if(rs.next()){
				ug =getEntityFactory( ).create( UsersGroup.class );
				ug = new UsersGroup();
				ug.setId(id);
				ug.setName(rs.getString("Name"));
				Integer parentId = rs.getInt("parent_id");
				
				if(!rs.wasNull() && parentId != id){
					UsersGroup parent = read(parentId);
					ug.setParent(parent);
				}
				cacheMap.put( id, ug );
			}
			return ug;
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				rs.close();
			} catch(SQLException | NullPointerException e) {}
			try {
				ps.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public void update(UsersGroup entity) throws PersistentException {
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `users_group` SET `name` = ?, `parent_id` = ? WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, entity.getName());
			UsersGroup parent = entity.getParent();
			if(parent != null && parent.getId() != null) {
				ps.setInt(2, parent.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}
			ps.setInt(3, entity.getId());
			
			ps.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				ps.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public void delete(Integer id) throws PersistentException {
		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM `users_group` WHERE `id` = ?";
			ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch(SQLException e) {
			throw new PersistentException(e);
		} finally {
			try {
				ps.close();
			} catch(SQLException | NullPointerException e) {}
		}
	}

	@Override
	public List< UsersGroup > readAll( ) throws PersistentException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UsersGroup> usersGroups= new ArrayList<UsersGroup>(  );
        try {
            //String sql = "SELECT * FROM users_group WHERE parent_id IS NULL ";
            String sql = "SELECT * FROM users_group";
            preparedStatement = getConnection().prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ){
                UsersGroup usersGroup=null;
                if ( cacheMap.containsKey( resultSet.getInt( "id" ) ) ) {
                    usersGroup = cacheMap.get( resultSet.getInt( "id" ) );
                }else{
                    usersGroup = getEntityFactory().create( UsersGroup.class );
                    usersGroup.setId( resultSet.getInt( "id" ));
                    cacheMap.put( resultSet.getInt( "id" ), usersGroup );
                }
                usersGroup.setName( resultSet.getString( "name" ) );
                Integer parentId = resultSet.getInt("parent_id");
                if(!resultSet.wasNull() && parentId != usersGroup.getId()){
                    UsersGroup parent = read(parentId);
                    usersGroup.setParent(parent);
                }
                usersGroups.add( usersGroup );
            }
            return usersGroups;
        }  catch ( SQLException e ) {
			logger.error( "Reading of record was failed. Table 'users_groups'", e );
			throw new PersistentException( e );
		} finally {
			try {
				resultSet.close( );
			} catch ( SQLException | NullPointerException e ) {
			}
			try {
				preparedStatement.close( );
			} catch ( SQLException | NullPointerException e ) {
			}
		}
	}

	public List<UsersGroup> readPossibleParents(int id) throws PersistentException {
	      if ( cacheMap.containsKey( id ) ) {
	            return (List<UsersGroup>) cacheMap.get( id );
	        }		
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT `name`, `parent_id` FROM `users_group` WHERE `id` != ?";
				ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
				
				ps.setInt(1, id);
				
				rs = ps.executeQuery();
				UsersGroup ug = null;
				if(rs.next()){
					ug =getEntityFactory( ).create( UsersGroup.class );
					ug = new UsersGroup();
					ug.setId(id);
					ug.setName(rs.getString("Name"));
					Integer parentId = rs.getInt("parent_id");
					
					if(!rs.wasNull() && parentId != id){
						UsersGroup parent = read(parentId);
						ug.setParent(parent);
					}
					cacheMap.put( id, ug );
				}
				return (List<UsersGroup>) ug;
			} catch(SQLException e) {
				throw new PersistentException(e);
			} finally {
				try {
					rs.close();
				} catch(SQLException | NullPointerException e) {}
				try {
					ps.close();
				} catch(SQLException | NullPointerException e) {}
			}
	}
}
