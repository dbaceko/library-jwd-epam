package by.batseko.library.command;

import by.batseko.library.command.reciever.page.*;
import by.batseko.library.command.reciever.user.LogInCommand;
import by.batseko.library.command.reciever.user.LogOutCommand;
import by.batseko.library.command.reciever.user.RegisterUserCommand;
import by.batseko.library.command.reciever.user.UpdateUserInfoCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public enum CommandStorage {
    ERROR_PAGE(new ErrorPage(), "errorPage"),
    HOME_PAGE(new HomePage(), "indexPage"),
    REGISTER_PAGE(new RegisterPage(), "registerPage"),
    LOG_IN_PAGE(new LogInPage(), "logInPage"),
    PROFILE_PAGE(new ProfilePage(), "profilePage"),
    ADMIN_PAGE(new AdminPage(), "adminPage"),

    SWITCH_LANG(new SwitchLanguageCommand(), "switch-lang"),
    REGISTER_USER(new RegisterUserCommand(), "registerUser"),
    UPDATE_PROFILE_USER(new UpdateUserInfoCommand(), "updateUserInfo"),
    LOG_IN(new LogInCommand(), "logIn"),
    LOG_OUT(new LogOutCommand(), "logOut"),

    ;
    private static final Logger LOGGER = LogManager.getLogger(CommandStorage.class);

    private final Command command;
    private final String commandName;

    CommandStorage(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    public static Command getCommandByName(String commandName) {
        for (CommandStorage type: CommandStorage.values()) {
            if (type.commandName.equals(commandName)){
                return type.command;
            }
        }
        LOGGER.warn(String.format("Command %s is not found, forward to HomePage", commandName));
        return new HomePage();
    }
}
