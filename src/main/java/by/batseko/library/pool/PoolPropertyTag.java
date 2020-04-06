package by.batseko.library.pool;

public enum PoolPropertyTag {
    URL("url"),
    LOGIN("login"),
    PASSWORD("password"),
    POOL_SIZE("poolSize"),
    CONNECTION_AWAITING_TIMEOUT("connectionAwaitingTimeout");

    private String field;

    PoolPropertyTag(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
