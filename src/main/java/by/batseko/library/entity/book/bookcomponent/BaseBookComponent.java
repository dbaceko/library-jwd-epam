package by.batseko.library.entity.book.bookcomponent;

import java.util.UUID;

public class BaseBookComponent {
    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void defineUUID() {
        uuid = UUID.randomUUID().toString();
    }
}
