package hu.eteosf.gergokovacs.userorders.service.mapper;

import static hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity.OrderEntitySatus;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity;

public class OrderEntityMapper {
    public static OrderDto toOrderDto(OrderEntity orderEntity) {
        return new OrderDto(orderEntity.getOrderId(), toOrderDtoStatus(orderEntity.getOrderStatus()),
                            ProductEntityMapper.toListOfProductDtos(orderEntity.getProducts()));
    }

    public static OrderEntity toOrderEntity(OrderDto orderDto) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderDto.getOrderId());
        orderEntity.setOrderStatus(toOrderEntityStatus(orderDto.getOrderStatus()));
        orderEntity.setProducts(ProductEntityMapper.toListOfProductEntities(orderDto.getProducts()));

        return orderEntity;
    }

    public static List<OrderDto> toListOfOrderDtos(List<OrderEntity> list) {
        if (list == null) return null;
        final List<OrderDto> resultList = new ArrayList<>();
        for (OrderEntity orderEntity : list) {
            final OrderDto orderDto = toOrderDto(orderEntity);
            resultList.add(orderDto);
        }
        return resultList;
    }

    public static List<OrderEntity> toListOfOrderEntities(List<OrderDto> list) {
        if (list == null) return null;
        final List<OrderEntity> resultList = new ArrayList<>();
        for (OrderDto orderDto : list) {
            final OrderEntity orderEntity = toOrderEntity(orderDto);
            resultList.add(orderEntity);
        }
        return resultList;
    }

    private static OrderDto.OrderDtoStatus toOrderDtoStatus(OrderEntitySatus status) {
        switch (status) {
            case SHIPPED:
                return OrderDto.OrderDtoStatus.SHIPPED;
            case RECEIVED:
                return OrderDto.OrderDtoStatus.RECEIVED;
            case DELIVERED:
                return OrderDto.OrderDtoStatus.DELIVERED;
            default:
                return null;
        }
    }

    private static OrderEntitySatus toOrderEntityStatus(OrderDto.OrderDtoStatus status) {
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
