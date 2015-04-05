package by.vsu.mf.ammc.pm.dao.mysql.project.specification;

import by.vsu.mf.ammc.pm.dao.abstraction.project.specification.RequirementDao;
import by.vsu.mf.ammc.pm.dao.mysql.BaseDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.EntityFactory;
import by.vsu.mf.ammc.pm.domain.project.Module;
import by.vsu.mf.ammc.pm.domain.project.specification.Requirement;
import by.vsu.mf.ammc.pm.domain.project.specification.UseCase;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;


public class RequirementDaoImpl extends BaseDaoImpl implements RequirementDao  {
    private static Logger logger = Logger.getLogger( RequirementDaoImpl.class);
    
    private Map<Integer, Requirement> identityMap = new HashMap<Integer, Requirement>(); 
    
    @Override
    public Integer create(Requirement entity) throws PersistentException {
        String sql = " INSERT INTO requirement ( name, description, importance, change_probability, use_case_id, module_id ) VALUES ( ?, ?, ?, ?, ?, ? ); ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString( 1, entity.getName() );
            preparedStatement.setString( 2, entity.getDescription() );
            preparedStatement.setFloat(3, entity.getImportance());
            preparedStatement.setFloat(4, entity.getChangeProbability());
            if (  entity.getUseCase() != null  &&  entity.getUseCase().getId() != null ) {
                preparedStatement.setInt( 5, entity.getUseCase().getId() );
            } else {
                preparedStatement.setNull( 5, Types.INTEGER);
            }
            if ( entity.getModule() != null && entity.getModule().getId() != null ) {
                preparedStatement.setInt( 6, entity.getModule().getId() );
            } else {
                preparedStatement.setNull( 6, Types.INTEGER );
            }
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if( resultSet.next() ) {
                return resultSet.getInt( 1 );
            } else {
                logger.error( "There is problem with index after trying to add record into table 'requirement'" );
                throw new PersistentException();
            }
        } catch ( SQLException e ){
            logger.error( "Creation of record was failed. Table 'requirement'", e );
            throw new PersistentException( e );
        } finally {
            try {
                resultSet.close();
            } catch ( SQLException | NullPointerException e){}
            try {
                preparedStatement.close();
            } catch ( SQLException | NullPointerException e){}
        }
    }

    @Override
    public Requirement read(Integer id) throws PersistentException {
    	if(identityMap.containsKey(id)){
    		return identityMap.get(id);
    	}
        String sql = " SELECT name, description, importance, change_probability, use_case_id, module_id FROM requirement WHERE id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	
            preparedStatement = getConnection().prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt( 1 , id );
            resultSet = preparedStatement.executeQuery();
            Requirement requirement = getEntityFactory().create(Requirement.class);
            if( resultSet.next() ) {
                requirement.setId( id );
                requirement.setName( resultSet.getString("name") );
                requirement.setDescription( resultSet.getString( "description") );
                requirement.setImportance( resultSet.getFloat( "importance" ) );
                requirement.setChangeProbability( resultSet.getFloat( "change_probability" ) );
                Integer useCaseId = resultSet.getInt( "use_case_id" );
                if( !resultSet.wasNull() ) {
                    UseCase useCase = getEntityFactory().create(UseCase.class);;
                    useCase.setId( useCaseId );
                    requirement.setUseCase( useCase );
                }
                Integer moduleId = resultSet.getInt( "module_id" );
                if( !resultSet.wasNull() ) {
                    Module module = getEntityFactory().create(Module.class);;
                    module.setId(moduleId);
                    requirement.setModule(module);
                }
            }
            identityMap.put(id, requirement);
            return requirement;
        } catch( SQLException e) {
            logger.error( "Reading of record was failed. Table 'requirement'", e);
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch ( SQLException | NullPointerException e) {}
            try {
                preparedStatement.close();
            } catch ( SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void update(Requirement entity) throws PersistentException {
        String sql = " UPDATE requirement SET name = ?, description = ?, importance = ?, change_probability = ?, use_case_id = ?, module_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement( sql );

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString( 2, entity.getDescription() );
            preparedStatement.setFloat( 3 , entity.getImportance() );
            preparedStatement.setFloat( 4, entity.getChangeProbability() );
            if( entity.getUseCase() != null && entity.getUseCase().getId() != null ) {
                preparedStatement.setInt( 5 , entity.getUseCase().getId());
            } else  {
                preparedStatement.setNull( 5 , Types.INTEGER );
            }
            if( entity.getModule() != null && entity.getModule().getId() != null ) {
                preparedStatement.setInt( 6 , entity.getModule().getId());
            } else  {
                preparedStatement.setNull( 6 , Types.INTEGER );
            }
            preparedStatement.setInt( 7, entity.getId() );
            preparedStatement.executeUpdate();
        } catch( SQLException e) {
            logger.error( "Updating of record was failed. Table 'requirement'", e);
            throw new PersistentException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch ( SQLException | NullPointerException e) {}
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = " DELETE FROM requirement WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement( sql );
            preparedStatement.setInt( 1, id );
            preparedStatement.executeUpdate();

        }catch ( SQLException e) {
            logger.error( "Deleting of record was failed. Table 'requirement' ", e);
            throw new PersistentException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch ( SQLException  | NullPointerException e) {}
        }
    }
}
