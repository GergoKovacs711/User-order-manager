package hu.eteosf.gergokovacs.userorders.model.dto;

import java.util.Collections;
import java.util.List;

public class OrderDto {
    private final String orderId;
    private final OrderDtoSatus orderStatus;
    private final List<ProductDto> products;

    public OrderDto(String orderId, OrderDtoSatus orderStatus, List<ProductDto> products) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderDtoSatus getOrderStatus() {
        return orderStatus;
    }

    public List<ProductDto> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public enum OrderDtoSatus {
        RECEIVED("RECEIVED"),
        SHIPPED("SHIPPED"),
        DELIVERED("DELIVERED");

        private String value;
        OrderDtoSatus(String value) {
            this.value = value;
        }
        public String toString() {
            return String.valueOf(this.value);
        }
    }
}
