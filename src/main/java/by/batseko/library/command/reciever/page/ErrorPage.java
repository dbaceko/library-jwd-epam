package by.batseko.library.command.reciever.page;

import by.batseko.library.command.Command;
import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.command.PageStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorPage implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(JSPAttributeStorage.PAGE, PageStorage.ERROR);
        return PageStorage.ERROR;
    }
}