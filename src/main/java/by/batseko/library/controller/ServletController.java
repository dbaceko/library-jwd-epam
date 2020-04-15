package by.batseko.library.controller;

import by.batseko.library.command.Command;
import by.batseko.library.command.CommandStorage;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.Router;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.ControllerRuntimeException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controller", urlPatterns = {"/controller", "/jsp/controller"})
public class ServletController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ServletController.class);



    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().init();
        } catch (ConnectionPoolException e) {
            LOGGER.fatal(e);
            throw new ControllerRuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(JSPAttributeStorage.GET_METHOD);
        handleRequest(req, resp, commandName);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(JSPAttributeStorage.POST_METHOD);
        handleRequest(req, resp, commandName);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
        super.destroy();
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response, String commandName) throws ServletException, IOException {
        Command command = CommandStorage.getCommandByName(commandName);
        Router router = command.execute(request, response);
        LOGGER.info(String.format("%s <- page", router.getPagePath()));
        if (router.getRouteType().equals(Router.RouteType.FORWARD)) {
            request.getRequestDispatcher(router.getPagePath()).forward(request,response);
        } else {
            response.sendRedirect(request.getContextPath() + router.getPagePath());
        }
    }
}
