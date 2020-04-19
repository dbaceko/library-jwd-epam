package by.batseko.library.entity.user;

import by.batseko.library.command.SupportedLocaleStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum UserRole {
    ADMIN(1),
    USER(2),
    GUEST(3);

    private static final Logger LOGGER = LogManager.getLogger(SupportedLocaleStorage.class);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole getRoleById(int id) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.id == id) {
                return userRole;
            }
        }
        LOGGER.warn(String.format("Role with id: %d is not found, default role is %s", id, GUEST));
        return GUEST;
    }
}
