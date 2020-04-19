package by.batseko.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    String uuid;
    String name;
    String surname;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return  Objects.equals(uuid, author.uuid) &&
                Objects.equals(name, author.name) &&
                Objects.equals(surname, author.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, surname);
    }

    @Override
    public String toString() {
        return "Author{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
