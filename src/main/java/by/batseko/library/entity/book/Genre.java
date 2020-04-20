package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {
    private String uuid;
    private String genre;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return Objects.equals(uuid, genre1.uuid) &&
                Objects.equals(genre, genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, genre);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "uuid=" + uuid +
                ", genre='" + genre + '\'' +
                '}';
    }
}
