package kni.webstore.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kni.webstore.security.model.User;
import kni.webstore.security.service.UserService;

@Component
public class RegisterUserValidator implements Validator {

	@Autowired
	private UserService userService;

	private static int USERNAME_MIN = 4;
	private static int USERNAME_MAX = 16;
	private static String USERNAME_LENGTH_MSG = "Nazwa użytkownika min. " + USERNAME_MIN + " i max. " + USERNAME_MAX
			+ " znaków";

	private static int PASSWORD_MIN = 6;
	private static int PASSWORD_MAX = 16;
	private static String PASSWORD_LENGTH_MSG = "Hasło min. " + PASSWORD_MIN + " i max. " + PASSWORD_MAX + " znaków";

	private static int NAME_MIN = 4;
	private static int NAME_MAX = 24;
	private static String NAME_LENGTH_MSG = "Imie i nazwisko min. " + NAME_MIN + " i max. " + NAME_MAX + " znaków";

	private static int EMAIL_MIN = 6;
	private static int EMAIL_MAX = 48;
	private static String EMAIL_LENGTH_MSG = "Email min. " + EMAIL_MIN + " i max. " + EMAIL_MAX + " znaków";

	private static String EMAIL_MONKEY_MSG = "Nieprawidłowy format adresu Email";
	private static String USERNAME_ONLY_DIGITS_MSG = "Nazwa użytkownika nie może zawierać tylko liczb.";
	private static String PASSWORD_NEEDS_DIGIT = "Hasło musi zawierać conajmniej jedną liczbę.";
	
	@Override
	public void validate(Object objUser, Errors errors) {
		if (objUser == null) {
			errors.reject("null_error");
			return;
		}
		if (!supports(objUser.getClass())) {
			errors.reject("type_error");
			return;
		}

		User user = (User) objUser;
		checkFieldsLength(errors, user);
		rejectIfUsernameHasOnlyNumbers(errors, user.getUsername());
		validEmail(errors, user.getEmail());
		checkThatPasswordHasDigit(errors, user.getPassword());
		rejectIfUsernameExists(errors, user.getUsername());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	private void checkFieldsLength(Errors errors, User user) {
		if (!hasLengthInInterval(USERNAME_MIN, USERNAME_MAX, user.getUsername()))
			errors.rejectValue("username", "username_size", USERNAME_LENGTH_MSG);

		if (!hasLengthInInterval(PASSWORD_MIN, PASSWORD_MAX, user.getPassword()))
			errors.rejectValue("password", "password_size", PASSWORD_LENGTH_MSG);

		if (!hasLengthInInterval(NAME_MIN, NAME_MAX, user.getFirstName()))
			errors.rejectValue("firstName", "first_name_size", NAME_LENGTH_MSG);

		if (!hasLengthInInterval(NAME_MIN, NAME_MAX, user.getLastName()))
			errors.rejectValue("lastName", "last_name_size", NAME_LENGTH_MSG);

		if (!hasLengthInInterval(EMAIL_MIN, EMAIL_MAX, user.getEmail()))
			errors.rejectValue("email", "email_size", EMAIL_LENGTH_MSG);
	}

	private boolean hasLengthInInterval(int min, int max, String str) {
		int length = str.length();
		return length >= min && length <= max;
	}

	private void rejectIfUsernameHasOnlyNumbers(Errors errors, String str) {
		if (hasOnlyNumbers(str))
			errors.rejectValue("username", "username_only_digits_error", USERNAME_ONLY_DIGITS_MSG);
	}

	private boolean hasOnlyNumbers(String name) {
		return name.matches("[0-9]+");
	}

	private void validEmail(Errors errors, String email) {
		if (!email.contains("@"))
			errors.rejectValue("email", "email_monkey_error", EMAIL_MONKEY_MSG);
	}

	private void checkThatPasswordHasDigit(Errors errors, String password) {
		char[] charTab = password.toCharArray();
		boolean hasDigit = false;

		for (char character : charTab) {
			if (Character.isDigit(character)) {
				hasDigit = true;
				break;
			}
		}
		if (!hasDigit)
			errors.rejectValue("password", "password_min_digit_error", PASSWORD_NEEDS_DIGIT);
	}

	private void rejectIfUsernameExists(Errors errors, String username) {
		User lookedFor = userService.findByUsername(username);
		if (lookedFor != null)
			errors.rejectValue("username", "exists_error");
	}
}
