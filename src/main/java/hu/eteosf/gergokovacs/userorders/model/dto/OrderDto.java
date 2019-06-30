package hu.eteosf.gergokovacs.userorders.model.dto;

import java.util.Collections;
import java.util.List;

public class OrderDto {
    private final String orderId;
    private final OrderDtoStatus orderStatus;
    private final List<ProductDto> products;

    public OrderDto(String orderId, OrderDtoStatus orderStatus, List<ProductDto> products) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderDtoStatus getOrderStatus() {
        return orderStatus;
    }

    public List<ProductDto> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public enum OrderDtoStatus {
        RECEIVED("RECEIVED"),
        SHIPPED("SHIPPED"),
        DELIVERED("DELIVERED");

        private String value;
        OrderDtoStatus(String value) {
            this.value = value;
        }
        public String toString() {
            return String.valueOf(this.value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto that = (OrderDto) o;
        return this.orderId.equals(that.getOrderId());
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" + "orderId='" + orderId + '\'' + ", orderStatus=" + orderStatus + ", products=" + products + '}';
    }
}
