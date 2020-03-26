package by.batseko.library.command;

import by.batseko.library.command.reciever.page.*;
import by.batseko.library.command.reciever.user.LogIn;
import by.batseko.library.command.reciever.user.LogOut;
import by.batseko.library.command.reciever.user.RegisterUser;


public enum CommandStorage {
    ERROR_PAGE(new ErrorPage(), "errorPage"),
    HOME_PAGE(new HomePage(), "indexPage"),
    REGISTER_PAGE(new RegisterPage(), "registerPage"),
    LOG_IN_PAGE(new LogInPage(), "logInPage"),
    SWITCH_LANG(new SwitchLanguage(), "switch-lang"),
    PROFILE_PAGE(new ProfilePage(), "profilePage"),

    REGISTER_USER(new RegisterUser(), "registerUser"),
    LOG_IN(new LogIn(), "logIn"),
    LOG_OUT(new LogOut(), "logOut"),

    ;

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
        return new HomePage();
    }
}
