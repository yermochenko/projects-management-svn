package by.vsu.mf.ammc.pm.service.main.user;

import java.util.List;

import org.apache.log4j.Logger;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactsTypeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.dao.mysql.project.specification.RequirementDaoImpl;
import by.vsu.mf.ammc.pm.dao.mysql.user.ContactsTypeDaoImpl;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.ContactsType;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactsTypeService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ContactsTypeServiceImpl extends ServiceImpl implements ContactsTypeService {
	
	private static Logger logger = Logger.getLogger(ContactsTypeServiceImpl.class);

	@Override
	public List<ContactsType> findAll() throws PersistentException {
		ContactsTypeDao ctdi = getTransaction().getDao(ContactsTypeDao.class);
		return ctdi.readAll();
	}

	@Override
	public ContactsType findById(int id) throws PersistentException {
		return this.getTransaction().getDao(ContactsTypeDao.class).read(id);
	}

	@Override
	public void save(ContactsType type) throws PersistentException {
		ContactsTypeDao contactsTypeDao = this.getTransaction().getDao(ContactsTypeDao.class);
		Integer id = type.getId();
		logger.debug(String.format("contactsType ID value: " + id));
		logger.debug(String.format("contactsType name value: " + type.getName()));
		logger.debug(String.format("contactsType regexp value: " + type.getRegexp()));
		if(id != null) {
			logger.debug(String.format("Updating"));
			contactsTypeDao.update(type);
		} else {
			logger.debug(String.format("Creating new instance"));
			type.setId(contactsTypeDao.create(type));
			contactsTypeDao.update(type);
		}
	}

	@Override
	public boolean canDelete(int id) throws PersistentException {
		ContactsType contactsType = this.getTransaction().getDao(ContactsTypeDao.class).read(id);
		logger.debug(String.format("contactsType ID value: " + contactsType.getId()));
		if(contactsType != null) {
			return true;
		} else {
			return false;
		}
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
