package hu.eteosf.gergokovacs.userorders.service.mapper;

import static hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity.OrderSatus;
import static hu.eteosf.gergokovacs.userorders.service.mapper.ProductMapper.toListOfProduct;
import static hu.eteosf.gergokovacs.userorders.service.mapper.ProductMapper.toListOfProductEntity;
import static io.swagger.model.Order.StatusEnum;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity;
import io.swagger.model.Order;

public class OrderMapper {
    public static Order toOrder(OrderEntity orderEntity) {
        final Order order = new Order();
        order.setOrderId(orderEntity.getOrderId());
        order.setStatus(toStatusEnum(orderEntity.getOrderStatus()));
        order.setProducts(toListOfProduct(orderEntity.getProducts()));

        return order;
    }

    public static OrderEntity toOrderEntity(Order order) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setOrderStatus(toOrderStatus(order.getStatus()));
        orderEntity.setProducts(toListOfProductEntity(order.getProducts()));

        return orderEntity;
    }

    public static List<Order> toListOfOrders(List<OrderEntity> list) {
        final List<Order> resultList = new ArrayList<>();
        for (OrderEntity orderEntity : list) {
            final Order order = toOrder(orderEntity);
            resultList.add(order);
        }
        return resultList;
    }

    public static List<OrderEntity> toListOfOrderEntities(List<Order> list) {
        final List<OrderEntity> resultList = new ArrayList<>();
        for (Order order : list) {
            final OrderEntity orderEntity = toOrderEntity(order);
            resultList.add(orderEntity);
        }
        return resultList;
    }

    private static StatusEnum toStatusEnum(OrderSatus status) {
        switch (status) {
            case SHIPPED:
                return StatusEnum.SHIPPED;
            case RECEIVED:
                return StatusEnum.RECEIVED;
            case DELIVERED:
                return StatusEnum.DELIVERED;
            default:
                return null;
        }
    }

    private static OrderSatus toOrderStatus(StatusEnum status) {
        switch (status) {
            case SHIPPED:
                return OrderSatus.SHIPPED;
            case RECEIVED:
                return OrderSatus.RECEIVED;
            case DELIVERED:
                return OrderSatus.DELIVERED;
            default:
                return null;
        }
    }
}
