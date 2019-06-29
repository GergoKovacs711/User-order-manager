package hu.eteosf.gergokovacs.userorders.service;

import java.util.List;

import io.swagger.model.Order;
import io.swagger.model.User;

public interface UserService {
    User getUser(String userId);
    List<User> getAllUser();
    void createUser(User user);
    void deleteUser(String userId);
    void updateUser(String userId, User user);

    List<Order> getAllOrdersOfUser(String userId);
    void createOrderOfUser(String userId, Order order);
    Order getOrderOfUser(String userId, String orderId);
    void deleteOrderOfUser(String userId, String orderId);
    void updateOrderOfUser(String userId, String orderId, Order order);
}
