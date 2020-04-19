package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class BookLanguage implements Serializable {
    private int id;
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLanguage bookLanguage1 = (BookLanguage) o;
        return getId() == bookLanguage1.getId() &&
                Objects.equals(getLanguage(), bookLanguage1.getLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLanguage());
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", language='" + language + '\'' +
                '}';
    }
}
