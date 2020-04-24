package by.batseko.library.entity.book.bookcomponent;

import java.io.Serializable;
import java.util.Objects;

public class Publisher extends BaseBookComponent implements Serializable {
    private String publisherTitle;

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
