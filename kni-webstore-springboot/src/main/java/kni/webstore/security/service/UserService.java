package kni.webstore.security.service;

import kni.webstore.security.model.User;

public interface UserService {
	public User findByUsername(String userName);
	public void save(User user);
}
