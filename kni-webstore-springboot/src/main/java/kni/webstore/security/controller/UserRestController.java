package kni.webstore.security.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.security.model.User;
import kni.webstore.security.service.UserService;
import kni.webstore.validators.RegisterUserValidator;

@RestController
public class UserRestController {
	
	private final Logger log = Logger.getLogger(UserRestController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegisterUserValidator userValidator;
	
	@PostMapping(path = "/register")
	public boolean registerUserAccount(@RequestBody User user, BindingResult errorLog) throws BindException {
		userValidator.validate(user, errorLog);

		if (!errorLog.hasErrors())
			userService.save(user);
		else
			throw new BindException(errorLog);

		log.info("Pomyślnie zarejestrowano użytkownika");
		return true;
	}
}
