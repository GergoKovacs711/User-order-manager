package hu.eteosf.gergokovacs.userorders.service.mapper.entity;

import static hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity.OrderEntitySatus;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity;

public class OrderEntityMapper {
    public static OrderDto toOrderDto(OrderEntity orderEntity) {
        return new OrderDto(orderEntity.getOrderId(), toOrderDtoStatus(orderEntity.getOrderStatus()), ProductEntityMapper.toListOfProductDtos(orderEntity.getProducts()));
    }

    public static OrderEntity toOrderEntity(OrderDto orderDto) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderDto.getOrderId());
        orderEntity.setOrderStatus(toOrderEntityStatus(orderDto.getOrderStatus()));
        orderEntity.setProducts(ProductEntityMapper.toListOfProductEntities(orderDto.getProducts()));

        return orderEntity;
    }

    public static List<OrderDto> toListOfOrderDtos(List<OrderEntity> list) {
        final List<OrderDto> resultList = new ArrayList<>();
        for (OrderEntity orderEntity : list) {
            final OrderDto orderDto = toOrderDto(orderEntity);
            resultList.add(orderDto);
        }
        return resultList;
    }

    public static List<OrderEntity> toListOfOrderEntities(List<OrderDto> list) {
        final List<OrderEntity> resultList = new ArrayList<>();
        for (OrderDto orderDto : list) {
            final OrderEntity orderEntity = toOrderEntity(orderDto);
            resultList.add(orderEntity);
        }
        return resultList;
    }

    private static OrderDto.OrderDtoSatus toOrderDtoStatus(OrderEntitySatus status) {
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

    private static OrderEntitySatus toOrderEntityStatus(OrderDto.OrderDtoSatus status) {
        switch (status) {
            case SHIPPED:
                return OrderEntitySatus.SHIPPED;
            case RECEIVED:
                return OrderEntitySatus.RECEIVED;
            case DELIVERED:
                return OrderEntitySatus.DELIVERED;
            default:
                return null;
        }
    }
}
