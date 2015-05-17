package by.vsu.mf.ammc.pm.service.main.user;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactsTypeService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ContactsTypeServiceImpl extends ServiceImpl implements ContactsTypeService {
	@Override
	public List<ContactsType> findAll() throws PersistentException {
		ContactsTypeDaoImpl ctdi = new ContactsTypeDaoImpl();
		return ctdi.readAll();
	}

	@Override
	public ContactsType findById(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ContactsType type) throws PersistentException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(int id) throws PersistentException {
		// TODO Auto-generated method stub
	}
}
