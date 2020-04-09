package by.batseko.library.validatior;

import by.batseko.library.entity.User;
import by.batseko.library.exception.ValidatorException;

public class UserValidator {
    private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{3,25}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{8,16}$";
    private static final String EMAIL_REGEX = "^(([a-z0-9_-]+)@([a-z0-9_-]+)\\.([a-z]{2,6}))$";
    private static final String PHONE_REGEX = "^[+]?[0-9]{7,15}$";
    private static final String PASSPORT_SN_REGEX = "^[A-Z]{2}[0-9]{7}$";
    private static final int MAX_EMAIL_FIELD_LENGTH = 45;
    private static final int MAX_FIELD_LENGTH = 25;

    public void validateNewUser(User user) throws ValidatorException {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhone(user.getPhoneNumber());
        validatePassportSN(user.getPassportSerialNumber());
        validateFieldLength(user.getFirstName(), user.getLastName(), user.getAddress());
    }

    public void validateUpdatedUser(User user) throws ValidatorException {
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhone(user.getPhoneNumber());
        validateFieldLength(user.getAddress());
    }

    private void validateLogin(String login) throws ValidatorException {
        if (login == null || !login.matches(LOGIN_REGEX)) {
            throw new ValidatorException("validation.registration.login");
        }
    }

    private void validatePhone(String phone) throws ValidatorException {
        if (phone == null || !phone.matches(PHONE_REGEX)) {
            throw new ValidatorException("validation.registration.phone");
        }
    }

    private void validatePassword(String password) throws ValidatorException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new ValidatorException("validation.registration.password");
        }
    }

    private void validateEmail(String email) throws ValidatorException {
        if (email == null || email.length() > MAX_EMAIL_FIELD_LENGTH || !email.matches(EMAIL_REGEX)) {
            throw new ValidatorException("validation.registration.email");
        }
    }

    private void validatePassportSN(String passport) throws ValidatorException {
        if (passport == null || !passport.matches(PASSPORT_SN_REGEX)) {
            throw new ValidatorException("validation.registration.passportSN");
        }
    }

    private void validateFieldLength(String... fields) throws ValidatorException {
        for (String field : fields) {
            if (field.length() > MAX_FIELD_LENGTH) {
                throw new ValidatorException("validation.registration.fieldlength");
            }
        }
    }
}
