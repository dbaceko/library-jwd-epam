package by.batseko.library.factory;

import by.batseko.library.util.EmailDistributorUtil;
import by.batseko.library.util.HashGeneratorUtil;

public class UtilFactory {
    private final HashGeneratorUtil hashGeneratorUtil;
    private final EmailDistributorUtil emailDistributorUtil;

    private UtilFactory() {
        hashGeneratorUtil = new HashGeneratorUtil();
        emailDistributorUtil = new EmailDistributorUtil();
    }

    private static class UtilFactorySingletonHolder {
        static final UtilFactory INSTANCE = new UtilFactory();
    }

    public static UtilFactory getInstance() {
        return UtilFactorySingletonHolder.INSTANCE;
    }

    public HashGeneratorUtil getHashGeneratorUtil() {
        return hashGeneratorUtil;
    }

    public EmailDistributorUtil getEmailDistributorUtil() {
        return emailDistributorUtil;
    }
}
