package by.batseko.library.filter;

import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;
import by.batseko.library.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "*.jsp")
public class UserRolePermittedPageFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(UserRolePermittedPageFilter.class);

    private static final Set<String> adminPages = new HashSet<>();
    private static final Set<String> userPages = new HashSet<>();
    private static final Set<String> guestPages = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        fillPermittedAdminPages();
        fillPermittedUserPages();
        fillPermittedGuestPages();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Role role = Role.valueOf(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).toString().toUpperCase());
        Set<String> permittedPages;
        switch (role) {
            case ADMIN:
                permittedPages = adminPages;
                break;
            case USER:
                permittedPages = userPages;
                break;
            case GUEST:
                permittedPages = guestPages;
                break;
            default:
                permittedPages = Collections.emptySet();
        }

        String requestPage = request.getRequestURI().replace(request.getContextPath(), "");

        if (permittedPages.contains(requestPage)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOGGER.info(String.format("Page is not in %s's Role scope: %s", role.name(), requestPage));
            response.sendRedirect(request.getContextPath() + PageStorage.HOME);
        }
    }

    private void fillPermittedAdminPages() {
        adminPages.add(PageStorage.ROOT);
        adminPages.add(PageStorage.ROOT_HOME);
        adminPages.add(PageStorage.HOME);
        adminPages.add(PageStorage.ONLINE_USERS_LIST);
        adminPages.add(PageStorage.ERROR);
        adminPages.add(PageStorage.PROFILE_USER);
    }

    private void fillPermittedUserPages() {
        userPages.add(PageStorage.ROOT);
        userPages.add(PageStorage.ROOT_HOME);
        userPages.add(PageStorage.HOME);
        userPages.add(PageStorage.ERROR);
        userPages.add(PageStorage.PROFILE_USER);
    }

    private void fillPermittedGuestPages() {
        guestPages.add(PageStorage.ROOT);
        guestPages.add(PageStorage.ROOT_HOME);
        guestPages.add(PageStorage.HOME);
        guestPages.add(PageStorage.LOG_IN);
        guestPages.add(PageStorage.REGISTER_USER);
    }
}