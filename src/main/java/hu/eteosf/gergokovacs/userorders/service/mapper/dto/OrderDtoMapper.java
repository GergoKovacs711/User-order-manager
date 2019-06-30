package hu.eteosf.gergokovacs.userorders.service.mapper.dto;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import io.swagger.model.Order;

public class OrderDtoMapper {
    public static Order toOrder(OrderDto orderDto) {
        final Order order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setStatus(toStatusEnum(orderDto.getOrderStatus()));
        order.setProducts(ProductDtoMapper.toListOfProducts(orderDto.getProducts()));

        return order;
    }

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(order.getOrderId(), toOrderDtoStatus(order.getStatus()), ProductDtoMapper.toListOfProductDtos(order.getProducts()));
    }

    public static List<Order> toListOfOrders(List<OrderDto> list) {
        final List<Order> resultList = new ArrayList<>();
        for (OrderDto orderDto : list) {
            final Order order = toOrder(orderDto);
            resultList.add(order);
        }
        return resultList;
    }

    public static List<OrderDto> toListOfOrderDtos(List<Order> list) {
        final List<OrderDto> resultList = new ArrayList<>();
        for (Order order : list) {
            final OrderDto orderDto = toOrderDto(order);
            resultList.add(orderDto);
        }
        return resultList;
    }

    private static Order.StatusEnum toStatusEnum(OrderDto.OrderDtoSatus status) {
        switch (status) {
            case SHIPPED:
                return Order.StatusEnum.SHIPPED;
            case RECEIVED:
                return Order.StatusEnum.RECEIVED;
            case DELIVERED:
                return Order.StatusEnum.DELIVERED;
            default:
                return null;
        }
    }

    private static OrderDto.OrderDtoSatus toOrderDtoStatus(Order.StatusEnum status) {
        switch (status) {
            case SHIPPED:
                return OrderDto.OrderDtoSatus.SHIPPED;
            case RECEIVED:
                return OrderDto.OrderDtoSatus.RECEIVED;
            case DELIVERED:
                return OrderDto.OrderDtoSatus.DELIVERED;
            default:
                return null;
        }
    }

}
