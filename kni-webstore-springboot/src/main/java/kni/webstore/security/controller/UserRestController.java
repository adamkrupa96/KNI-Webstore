package kni.webstore.security.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.security.exceptions.UserExistingException;
import kni.webstore.security.model.User;
import kni.webstore.security.service.UserService;

@RestController
public class UserRestController {
	
	private final Logger log = Logger.getLogger(UserRestController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/register")
	public boolean registerUserAccount(@RequestBody @Valid User validateUser) throws UserExistingException {

		User existing = userService.findByUsername(validateUser.getUsername());
		if (existing != null) {
			log.info("Istnieje juz konto o ponadej nazwie uzytkownika!");
			throw new UserExistingException();
		}

		userService.save(validateUser);
		log.info("zapisano uzytkownika");
		return true;
	}
}
