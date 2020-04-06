package by.batseko.library.entity;

import by.batseko.library.exception.EnumCastException;

public enum Role {
    ADMIN(1),
    USER(2);

    Role(int id) {
        this.id = id;
    }

    private final int id;

    public int getId() {
        return id;
    }

    public static Role getRoleById(int id) throws EnumCastException {
        for (Role role: Role.values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new EnumCastException("Role by id is not found");
    }
}
