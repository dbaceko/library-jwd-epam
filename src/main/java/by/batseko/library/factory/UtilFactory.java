package by.batseko.library.factory;

import by.batseko.library.util.EmailDistributor;
import by.batseko.library.util.Encryption;

public class UtilFactory {
    private final Encryption encryption;
    private final EmailDistributor emailDistributor;

    private UtilFactory() {
        encryption = new Encryption();
        emailDistributor = new EmailDistributor();
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

    public EmailDistributor getEmailDistributor() {
        return emailDistributor;
    }
}
