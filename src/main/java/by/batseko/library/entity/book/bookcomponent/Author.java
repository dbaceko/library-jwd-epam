package by.batseko.library.entity.book.bookcomponent;

import java.io.Serializable;
import java.util.Objects;

public class Author extends BaseBookComponent implements Serializable {
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return  Objects.equals(uuid, author.uuid) &&
                Objects.equals(this.authorName, author.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, authorName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "uuid=" + uuid +
                ", author='" + authorName + '\'' +
                '}';
    }
}
