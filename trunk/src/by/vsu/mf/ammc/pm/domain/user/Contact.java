package by.vsu.mf.ammc.pm.domain.user;

import by.vsu.mf.ammc.pm.domain.NamedEntity;

public class Contact extends NamedEntity {
	private User user;
	private ContactType type;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}
}
