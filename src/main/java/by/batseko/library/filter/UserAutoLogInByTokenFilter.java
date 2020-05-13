package by.batseko.library.filter;


import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.entity.user.User;
import by.batseko.library.entity.user.UserRole;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller", "*.jsp"})
public class UserAutoLogInByTokenFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(UserAutoLogInByTokenFilter.class);

    UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String userRole = (String) request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE);
        if(userRole.equals(UserRole.GUEST.name())) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie: cookies) {
                    if (cookie.getName().equals(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN)) {
                        String token = cookie.getValue();
                        try {
                            User user = userService.logInByToken(token);
                            LOGGER.info(user);
                            request.getSession().setAttribute(JSPAttributeStorage.USER_LOGIN, user.getLogin());
                            request.getSession().setAttribute(JSPAttributeStorage.USER_ROLE, user.getUserRole().name());
                            request.getSession().setAttribute(JSPAttributeStorage.USER_ID, user.getId());
                            request.getRequestDispatcher(PageStorage.HOME).forward(servletRequest, servletResponse);
                            return;
                        } catch (LibraryServiceException e) {
                            LOGGER.warn(String.format("RememberToken is invalid, %s", token), e);
                            cookie.setMaxAge(0);
                            cookie.setPath(request.getContextPath());
                            cookie.setValue("");
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
