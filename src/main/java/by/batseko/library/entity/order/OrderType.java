package by.batseko.library.entity.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OrderType {
    INVALID(0, ""),
    SUBSCRIPTION(1, ""),
    READING_HOLE(2, "");

    private static final Logger LOGGER = LogManager.getLogger(OrderType.class);

    private final int id;
    private final String localizedName;

    OrderType(int id, String localizedName) {
        this.id = id;
        this.localizedName = localizedName;
    }

    public int getId() {
        return id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public static OrderType getOrderTypeById(int id) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.id == id) {
                return orderType;
            }
        }
        LOGGER.warn(String.format("OrderType with id: %d is not found, default OrderType is %s", id, INVALID));
        return INVALID;
    }
}
