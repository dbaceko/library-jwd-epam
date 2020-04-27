package by.batseko.library.entity.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderStatus {
    INVALID(0, ""),
    PENDING(1, ""),
    ISSUED_BY(2, ""),
    RETURNED(3, ""),
    CLOSE(4, "");

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
