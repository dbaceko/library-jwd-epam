package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Publisher implements Serializable {
    private int id;
    private String publisherTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == publisher1.id &&
                Objects.equals(publisherTitle, publisher1.publisherTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publisherTitle);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publisher='" + publisherTitle + '\'' +
                '}';
    }
}
