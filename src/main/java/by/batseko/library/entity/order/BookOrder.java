package by.batseko.library.entity.order;

import by.batseko.library.builder.BookOrderBuilder;
import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.entity.user.User;

import java.sql.Timestamp;
import java.util.Objects;

public class BookOrder {
    private String uuid;
    private User user;
    private BookInstance bookInstance;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private Timestamp data;

    public BookOrder(){}

    public BookOrder(BookOrderBuilder bookOrderBuilder) {
        this.uuid = bookOrderBuilder.getUuid();
        this.user = bookOrderBuilder.getUser();
        this.bookInstance = bookOrderBuilder.getBookInstance();
        this.orderType = bookOrderBuilder.getOrderType();
        this.orderStatus = bookOrderBuilder.getOrderStatus();
        this.data = bookOrderBuilder.getData();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public void setBookInstance(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookOrder bookOrder = (BookOrder) o;
        return Objects.equals(user, bookOrder.user) &&
                Objects.equals(uuid, bookOrder.uuid) &&
                Objects.equals(bookInstance, bookOrder.bookInstance) &&
                orderType == bookOrder.orderType &&
                orderStatus == bookOrder.orderStatus &&
                Objects.equals(data, bookOrder.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, user, bookInstance, orderType, orderStatus, data);
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", user=" + user +
                ", bookInstance=" + bookInstance +
                ", orderType=" + orderType +
                ", orderStatus=" + orderStatus +
                ", data=" + data +
                '}';
    }
}