package by.vsu.mf.ammc.pm.service.main.user;

import java.util.ArrayList;
import java.util.List;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactsTypeDao;
import by.vsu.mf.ammc.pm.dao.abstraction.user.UserDao;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.domain.user.User;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ContactServiceImpl extends ServiceImpl implements ContactService {
	@Override
	public void save(Contact contact) throws PersistentException {
		ContactDao contactDao = getTransaction().getDao(ContactDao.class);
		Integer id = contact.getId();
		if(id != null){
			contactDao.update(contact);
		}else{
			contact.setId(contactDao.create(contact));
		}	
	}

	@Override
	public void delete(int id) throws PersistentException {
		// TODO Auto-generated method stub
	
				this.getTransaction().getDao(ContactDao.class).delete(id);
			
	}

	@Override
	public List<Contact> getContactByUser(User user) throws PersistentException {
		List<Contact> contacts = new ArrayList<Contact>();
		ContactDao contactDao = getTransaction().getDao(ContactDao.class);
		ContactsTypeDao contactsTypeDao = getTransaction().getDao(ContactsTypeDao.class);
		
		contacts = contactDao.getContactsByUser(user);
		for(Contact contact : contacts){
			contact.setType(contactsTypeDao.read(contact.getType().getId()));
		}
		return contacts;
	}
}
