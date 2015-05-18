package by.vsu.mf.ammc.pm.service.main.user;

import by.vsu.mf.ammc.pm.dao.abstraction.user.ContactDao;
import by.vsu.mf.ammc.pm.domain.user.Contact;
import by.vsu.mf.ammc.pm.exception.PersistentException;
import by.vsu.mf.ammc.pm.service.abstraction.user.ContactService;
import by.vsu.mf.ammc.pm.service.main.ServiceImpl;

public class ContactServiceImpl extends ServiceImpl implements ContactService {
	@Override
	public void save(Contact contact) throws PersistentException {
		ContactDao contactDao = getTransaction().getDao(ContactDao.class);
		Integer id = contact.getId();
		if(id != null){
			Contact dbContact = contactDao.read(id);
			if(dbContact !=null){
				dbContact.setId(contact.getId());
				dbContact.setName(contact.getName());
				dbContact.setType(contact.getType());
				dbContact.setUser(contact.getUser());
				contactDao.update(dbContact);
			}
		}else{
			contact.setId(contactDao.create(contact));
		}	
	}

	@Override
	public void delete(int id) throws PersistentException {
		// TODO Auto-generated method stub
	}
}
