package by.batseko.library.command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);

    default void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(JSPAttributeStorage.EXCEPTION_MESSAGE, message);
    }
}
