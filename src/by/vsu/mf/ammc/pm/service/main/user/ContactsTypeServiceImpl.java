package by.vsu.mf.ammc.pm.service.main.user;

import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactsTypeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
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
		return this.getTransaction().getDao(ContactsTypeDao.class).read(id);
	}

	@Override
	public void save(ContactsType type) throws PersistentException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		ContactsType contactsType = this.getTransaction().getDao(ContactsTypeDao.class).read(id);
		return contactsType !=null && contactsType.getName().isEmpty() && contactsType.getRegexp().isEmpty();
	}

	@Override
	public void delete(int id) throws PersistentException {
		try {
			if(canDelete(id)) {
				this.getTransaction().getDao(ContactsTypeDao.class).delete(id);
			}
		} catch(PersistentException e) {}
	}
}
