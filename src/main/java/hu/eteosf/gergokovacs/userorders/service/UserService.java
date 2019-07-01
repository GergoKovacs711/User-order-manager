package hu.eteosf.gergokovacs.userorders.service;

import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import io.swagger.model.User;

public interface UserService {
    UserDto getUser(String userId);
    List<UserDto> getAllUsers();
    void createUser(UserDto user);
    void deleteUser(String userId);
    void updateUser(String userId, User user);

    OrderDto getOrderOfUser(String userId, String orderId);
    List<OrderDto> getAllOrdersOfUser(String userId);
    void createOrderOfUser(String userId, OrderDto order);
    void deleteOrderOfUser(String userId, String orderId);
    void updateOrderOfUser(String userId, String orderId, OrderDto order);
}
