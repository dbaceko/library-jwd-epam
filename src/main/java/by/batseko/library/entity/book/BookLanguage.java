package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class BookLanguage implements Serializable {
    private String uuid;
    private String language;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        return Objects.equals(getUuid(), bookLanguage1.getUuid()) &&
                Objects.equals(getLanguage(), bookLanguage1.getLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getLanguage());
    }

    @Override
    public String toString() {
        return "Language{" +
                "uuid=" + uuid +
                ", language='" + language + '\'' +
                '}';
    }
}
