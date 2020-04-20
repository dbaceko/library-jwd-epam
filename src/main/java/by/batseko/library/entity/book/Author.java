package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private String uuid;
    private String fristname;
    private String lastname;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFristname() {
        return fristname;
    }

    public void setFristname(String fristname) {
        this.fristname = fristname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return  Objects.equals(uuid, author.uuid) &&
                Objects.equals(fristname, author.fristname) &&
                Objects.equals(lastname, author.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fristname, lastname);
    }

    @Override
    public String toString() {
        return "Author{" +
                "uuid=" + uuid +
                ", name='" + fristname + '\'' +
                ", surname='" + lastname + '\'' +
                '}';
    }
}
