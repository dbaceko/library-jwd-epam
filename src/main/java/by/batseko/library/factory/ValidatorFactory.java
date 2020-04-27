package by.batseko.library.factory;

import by.batseko.library.validatior.BookValidator;
import by.batseko.library.validatior.UserValidator;

public class  ValidatorFactory {
    private final UserValidator userValidator;
    private final BookValidator bookValidator;

    private ValidatorFactory() {
        userValidator = new UserValidator();
        bookValidator = new BookValidator();
    }

    private static class ValidatorFactorySingletonHolder {
        static final ValidatorFactory INSTANCE = new ValidatorFactory();
    }

    public static ValidatorFactory getInstance() {
        return ValidatorFactorySingletonHolder.INSTANCE;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public BookValidator getBookValidator() {
        return bookValidator;
    }
}
