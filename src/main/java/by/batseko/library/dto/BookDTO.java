package by.batseko.library.dto;

import by.batseko.library.entity.book.Book;

public class BookDTO {
    private Book book;
    private int totalBooksQuantity;
    private int totalAvailableBooksQuantity;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getTotalBooksQuantity() {
        return totalBooksQuantity;
    }

    public void setTotalBooksQuantity(int totalBooksQuantity) {
        this.totalBooksQuantity = totalBooksQuantity;
    }

    public int getTotalAvailableBooksQuantity() {
        return totalAvailableBooksQuantity;
    }

    public void setTotalAvailableBooksQuantity(int totalAvailableBooksQuantity) {
        this.totalAvailableBooksQuantity = totalAvailableBooksQuantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "book=" + book +
                ", totalBooksQuantity=" + totalBooksQuantity +
                ", totalAvailableBooksQuantity=" + totalAvailableBooksQuantity +
                '}';
    }
}
