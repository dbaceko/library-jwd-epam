package by.batseko.library.factory;

import by.batseko.library.validatior.UserValidator;

public class  ValidatorFactory {
    private final UserValidator userValidator;

    private ValidatorFactory() {
        userValidator = new UserValidator();
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
}
