package by.batseko.library.builder;

import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.order.OrderStatus;
import by.batseko.library.entity.order.OrderType;
import by.batseko.library.entity.user.User;

import java.sql.Timestamp;

public class BookOrderBuilder {
    private String uuid;
    private User user;
    private BookInstance bookInstance;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private Timestamp data;

    public String getUuid() {
        return uuid;
    }

    public BookOrderBuilder setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BookOrderBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public BookOrderBuilder setBookInstance(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BookOrderBuilder setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public BookOrderBuilder setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Timestamp getData() {
        return data;
    }

    public BookOrderBuilder setData(Timestamp data) {
        this.data = data;
        return this;
    }

    public BookOrder build() {
        return new BookOrder(this);
    }
}
