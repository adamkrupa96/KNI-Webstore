package kni.webstore.validator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import kni.webstore.security.model.User;
import kni.webstore.validators.RegisterUserValidator;

public class RegisterUserValidatorTest {

	private RegisterUserValidator userValid;
	private User user;
	private BindException errorLog;
	
	// Username min 4 - max 16 (not only digits)
	// Password min 6 - max 16 (include min 1 digit)
	// FirstName min 4 - max 24
	// Secname min 4 - max 24
	// email min 6 - 48 (include @)
	
	@Before
	public void setUp() throws Exception {
		userValid = new RegisterUserValidator();
		user = new User();
		user.setUsername("123m");
		user.setPassword("mmmmm6");
		user.setFirstName("1234");
		user.setLastName("1234");
		user.setEmail("w@ep.p");
		errorLog = new BindException(user, "user");
	}
	
	@Test
	public void minTest() {
		userValid.validate(user, errorLog);
		assertFalse(errorLog.hasErrors());
	}
	
	@Test
	public void maxTest() {
		user.setUsername(genStrinLength(16));
		user.setPassword(genStrinLength(15) + "1");
		user.setFirstName(genStrinLength(24));
		user.setLastName(genStrinLength(24));
		user.setEmail(genStrinLength(47) + "@");
		userValid.validate(user, errorLog);
		
		assertFalse(errorLog.hasErrors());
	}

	@Test
	public void overLengthTest() {
		user.setUsername(genStrinLength(20));
		user.setPassword(genStrinLength(15) + "11");
		user.setFirstName(genStrinLength(30));
		user.setLastName(genStrinLength(30));
		user.setEmail("@" + genStrinLength(50));
		
		userValid.validate(user, errorLog);
		
		assertTrue(hasError(errorLog, "username_size"));
		assertTrue(hasError(errorLog, "password_size"));
		assertTrue(hasError(errorLog, "first_name_size"));
		assertTrue(hasError(errorLog, "last_name_size"));
		assertTrue(hasError(errorLog, "email_size"));
	}
	
	@Test
	public void tooShortLengthTest() {
		user.setUsername("a");
		user.setPassword("a1");
		user.setFirstName("a");
		user.setLastName("a");
		user.setEmail("a@");
	
		userValid.validate(user, errorLog);
		
		assertTrue(hasError(errorLog, "username_size"));
		assertTrue(hasError(errorLog, "password_size"));
		assertTrue(hasError(errorLog, "first_name_size"));
		assertTrue(hasError(errorLog, "last_name_size"));
		assertTrue(hasError(errorLog, "email_size"));
	}
	
	@Test
	public void onlyDigitsUsername() {
		user.setUsername("123456");
		
		userValid.validate(user, errorLog);
		
		assertTrue(hasError(errorLog, "username_only_digits_error"));
	}
	
	@Test
	public void noDigitInPass() {
		user.setPassword("abcdefg");
		userValid.validate(user, errorLog);
		assertTrue(hasError(errorLog, "password_min_digit_error"));
	}
	
	@Test
	public void noMonkey() {
		user.setEmail("HEHEHEHEHHE");
		userValid.validate(user, errorLog);
		
		assertTrue(hasError(errorLog, "email_monkey_error"));
	}
	
	@Test
	public void existsTest() {
		user.setUsername("exist");
		userValid.validate(user, errorLog);
		
		assertTrue(hasError(errorLog, "exists_error"));
	}
	
	private boolean hasError(BindException errors, String code) {
		for (ObjectError error : errors.getAllErrors()) {
			if (error.getCode().equals(code)) return true;
		}
		return false;
	}
	
	private String genStrinLength(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += "a";
		}
		return str;
	}
	
}
