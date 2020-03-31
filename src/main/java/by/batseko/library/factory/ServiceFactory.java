package by.batseko.library.factory;

import by.batseko.library.service.UserService;
import by.batseko.library.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
    }

    private static class ServiceFactorySingletonHolder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return ServiceFactorySingletonHolder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }
}
