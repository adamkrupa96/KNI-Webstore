package kni.webstore.security.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kni.webstore.security.model.Authority;
import kni.webstore.security.model.AuthorityEnum;
import kni.webstore.security.model.User;
import kni.webstore.security.repository.AuthorityRepository;
import kni.webstore.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String userName) {
		return this.userRepository.findByUsername(userName);
	}
	
	public Authority getRole(AuthorityEnum role) {
		return authorityRepository.findByName(role);
	}

	@Override
	public void save(User user) {
		user.setAuthorities(Arrays.asList(getRole(AuthorityEnum.ROLE_USER)));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setLastPasswordResetDate(new Date());
		userRepository.save(user);
	}


}
