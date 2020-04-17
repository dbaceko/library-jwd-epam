package by.batseko.library.command;

import by.batseko.library.command.reciever.admin.ToggleUserBan;
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
    ADMIN_PAGE(new UsersOnlinePage(), "adminPage"),


    SWITCH_LANG(new SwitchLanguageCommand(), "switch-lang"),
    REGISTER_USER(new RegisterUserCommand(), "registerUser"),
    UPDATE_PROFILE_USER(new UpdateUserInfoCommand(), "updateUserInfo"),
    LOG_IN(new LogInCommand(), "logIn"),
    LOG_OUT(new LogOutCommand(), "logOut"),
    TOGGLE_USER_BAN(new ToggleUserBan(),"banUser")

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
        return getCommandEnumByName(commandName).command;
    }

    public static CommandStorage getCommandEnumByName(String commandName) {
        for (CommandStorage type: CommandStorage.values()) {
            if (type.commandName.equals(commandName)){
                return type;
            }
        }
        LOGGER.warn(String.format("Command %s is not found, forward to HomePage", commandName));
        return HOME_PAGE;
    }
}
