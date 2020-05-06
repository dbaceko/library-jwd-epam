package by.batseko.library.factory;

import by.batseko.library.util.EmailDistributor;
import by.batseko.library.util.HashGenerator;

public class UtilFactory {
    private final HashGenerator hashGenerator;
    private final EmailDistributor emailDistributor;

    private UtilFactory() {
        hashGenerator = new HashGenerator();
        emailDistributor = new EmailDistributor();
    }

    private static class UtilFactorySingletonHolder {
        static final UtilFactory INSTANCE = new UtilFactory();
    }

    public static UtilFactory getInstance() {
        return UtilFactorySingletonHolder.INSTANCE;
    }

    public HashGenerator getHashGenerator() {
        return hashGenerator;
    }

    public EmailDistributor getEmailDistributor() {
        return emailDistributor;
    }
}
