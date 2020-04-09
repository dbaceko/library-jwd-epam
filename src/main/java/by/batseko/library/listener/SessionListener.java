package by.batseko.library.listener;

import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.impl.ActiveUsersCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener  {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String userLogin = (String) se.getSession().getAttribute(JSPAttributeStorage.USER_LOGIN);
        LOGGER.info(String.format("Session is destroyed for %s", userLogin));
        ActiveUsersCache usersCache = ServiceFactory.getInstance().getUserService().getActiveUsersCache();
        usersCache.remove(userLogin);
    }
}
