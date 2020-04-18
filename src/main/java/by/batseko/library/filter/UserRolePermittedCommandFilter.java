package by.batseko.library.filter;

import by.batseko.library.command.CommandStorage;
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
import java.util.EnumSet;
import java.util.Set;

import static by.batseko.library.command.CommandStorage.*;

@WebFilter(urlPatterns = "/command")
public class UserRolePermittedCommandFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(UserRolePermittedCommandFilter.class);

    private static final Set<CommandStorage> guestCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            REGISTER_PAGE,
            SWITCH_LANG,
            REGISTER_USER,
            LOG_IN
    );

    private static final Set<CommandStorage> userCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            LOG_OUT,
            SWITCH_LANG,
            PROFILE_PAGE,
            UPDATE_PROFILE_USER
    );

    private static final Set<CommandStorage> adminCommands = EnumSet.of(
            HOME_PAGE,
            ERROR_PAGE,
            LOG_OUT,
            SWITCH_LANG,
            PROFILE_PAGE,
            UPDATE_PROFILE_USER,
            ADMIN_PAGE,
            TOGGLE_USER_BAN
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Role role = Role.valueOf(request.getSession().getAttribute(JSPAttributeStorage.USER_ROLE).toString().toUpperCase());
        Set<CommandStorage> permittedCommands;
        switch (role) {
            case ADMIN:
                permittedCommands = adminCommands;
                break;
            case USER:
                permittedCommands = userCommands;
                break;
            case GUEST:
                permittedCommands = guestCommands;
                break;
            default:
                permittedCommands = Collections.emptySet();
        }

        String commandName = request.getParameter(JSPAttributeStorage.COMMAND);
        CommandStorage command;
        if (commandName != null) {
            command = getCommandEnumByName(commandName);
        } else {
            command = HOME_PAGE;
        }

        if (permittedCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOGGER.info(String.format("Command is not in %s's Role scope: %s", role.name(), commandName));
            response.sendRedirect(request.getContextPath() + PageStorage.HOME);
        }
    }
}
