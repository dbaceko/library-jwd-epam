package by.batseko.library.factory;

import by.batseko.library.util.Encryption;
import by.batseko.library.validatior.UserValidator;

public class UtilFactory {
    private final Encryption encryption;

    private UtilFactory() {
        encryption = new Encryption();
    }

    private static class UtilFactorySingletonHolder {
        static final UtilFactory INSTANCE = new UtilFactory();
    }

    public static UtilFactory getInstance() {
        return UtilFactorySingletonHolder.INSTANCE;
    }

    public Encryption getEncryption() {
        return encryption;
    }
}
