package by.vsu.mf.ammc.pm.dao.abstraction.user;

import by.vsu.mf.ammc.pm.dao.abstraction.Dao;
import by.vsu.mf.ammc.pm.domain.user.UsersGroup;
import by.vsu.mf.ammc.pm.exception.PersistentException;

import java.util.List;

public interface UsersGroupDao extends Dao<UsersGroup> {
    List< UsersGroup > readAll( ) throws PersistentException;
}
