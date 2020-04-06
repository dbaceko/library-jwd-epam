package by.batseko.library.controller;

import by.batseko.library.command.Command;
import by.batseko.library.command.CommandStorage;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controller", urlPatterns = "/controller")
public class ServletController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ServletController.class);

    private static final String dbConnectionPool = "ConnectionPool.properties";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.getInstance().init(dbConnectionPool);
        } catch (ConnectionPoolException e) {
            LOGGER.fatal(e);
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
        super.destroy();
        ConnectionPool.getInstance().destroy();
    }


    private void handleRequest(HttpServletRequest request, HttpServletResponse response, String commandName) throws ServletException, IOException {
        Command command = CommandStorage.getCommandByName(commandName);
        String page = command.execute(request, response);
        LOGGER.info(page + "<- page");
        request.getRequestDispatcher(page).forward(request,response);
    }
}
