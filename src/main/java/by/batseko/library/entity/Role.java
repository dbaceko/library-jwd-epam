package by.batseko.library.entity;

import by.batseko.library.command.SupportedLocaleStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Role {
    ADMIN(1),
    USER(2),
    GUEST(3);

    private static final Logger LOGGER = LogManager.getLogger(SupportedLocaleStorage.class);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role getRoleById(int id) {
        for (Role role: Role.values()) {
            if (role.id == id) {
                return role;
            }
        }
        LOGGER.warn(String.format("Role with id: %d is not found, default role is %s", id, GUEST));
        return GUEST;
    }
}
