package by.batseko.library.factory;

import by.batseko.library.validatior.PoolValidator;
import by.batseko.library.validatior.UserValidator;

public class  ValidatorFactory {
    private final UserValidator userValidator;
    private final PoolValidator poolValidator;

    private ValidatorFactory() {
        userValidator = new UserValidator();
        poolValidator = new PoolValidator();
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

    public PoolValidator getPoolValidator() {
        return poolValidator;
    }
}
