package by.batseko.library.entity.book.bookcomponent;

import java.io.Serializable;
import java.util.Objects;

public class BookLanguage extends BaseBookComponent implements Serializable {
    private String languageTitle;

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLanguage bookLanguage1 = (BookLanguage) o;
        return Objects.equals(getUuid(), bookLanguage1.getUuid()) &&
                Objects.equals(getLanguageTitle(), bookLanguage1.getLanguageTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getLanguageTitle());
    }

    @Override
    public String toString() {
        return "Language{" +
                "uuid=" + uuid +
                ", language='" + languageTitle + '\'' +
                '}';
    }

    @Override
    public int compareTo(BaseBookComponent o) {
        return languageTitle.compareTo(((BookLanguage) o).languageTitle);
    }
}
