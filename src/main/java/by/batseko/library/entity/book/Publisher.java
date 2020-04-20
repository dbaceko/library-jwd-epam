package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Publisher implements Serializable {
    private String uuid;
    private String publisherTitle;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPublisherTitle() {
        return publisherTitle;
    }

    public void setPublisherTitle(String publisherTitle) {
        this.publisherTitle = publisherTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher1 = (Publisher) o;
        return Objects.equals(uuid, publisher1.uuid) &&
                Objects.equals(publisherTitle, publisher1.publisherTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, publisherTitle);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "uuid=" + uuid +
                ", publisher='" + publisherTitle + '\'' +
                '}';
    }
}
