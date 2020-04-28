package by.batseko.library.entity.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderStatus {
    INVALID(0, "orderStatus.invalid"),
    PENDING(1, "orderStatus.pending"),
    ISSUED_BY(2, "orderStatus.issuedBy"),
    RETURNED(3, "orderStatus.returned"),
    CLOSE(4, "orderStatus.close");

    private static final Logger LOGGER = LogManager.getLogger(OrderStatus.class);

    private final int id;
    private final String localizedName;

    OrderStatus(int id, String localizedName) {
        this.id = id;
        this.localizedName = localizedName;
    }

    public int getId() {
        return id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public static OrderStatus getOrderStatusById(int id) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.id == id) {
                return orderStatus;
            }
        }
        LOGGER.warn(String.format("OrderStatus with id: %d is not found, default OrderStatus is %s", id, INVALID));
        return INVALID;
    }
}
