package hu.eteosf.gergokovacs.userorders.service;

import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import io.swagger.model.Order;
import io.swagger.model.User;

public interface UserService {
    UserDto getUser(String userId);
    List<UserDto> getAllUser();
    void createUser(User user);
    void deleteUser(String userId);
    void updateUser(String userId, User user);

    List<OrderDto> getAllOrdersOfUser(String userId);
    void createOrderOfUser(String userId, Order order);
    OrderDto getOrderOfUser(String userId, String orderId);
    void deleteOrderOfUser(String userId, String orderId);
    void updateOrderOfUser(String userId, String orderId, Order order);
}
