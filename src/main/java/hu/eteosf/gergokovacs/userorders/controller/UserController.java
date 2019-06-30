package hu.eteosf.gergokovacs.userorders.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import hu.eteosf.gergokovacs.userorders.service.UserService;
import io.swagger.api.UsersApi;
import io.swagger.model.Order;
import io.swagger.model.User;

@RestController
public class UserController implements UsersApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> updateOrderById(@Valid Order body, String userId, String orderId) {
        LOGGER.debug("in UserController.updateOrderById(body: " + body.toString() + ", userId: " + userId + ", orderId: " + orderId + ")");

        userService.updateOrderOfUser(userId, orderId, body);

        LOGGER.info("Order update is successful");
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createOrderOfUser(@Valid Order body, String userId) {
        LOGGER.debug("in UserController.createOrderOfUser(body: " + body.toString() + ", userId: " + userId + ")");

        userService.createOrderOfUser(userId, body);

        LOGGER.info("Creating order is successful");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> createUser(@Valid User body) {
        LOGGER.debug("in UserController.createUser(body: " + body.toString() + ")");

        userService.createUser(body);

        LOGGER.info("Creating user is successful");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteOrderByIdOfUser(String userId, String orderId) {
        LOGGER.debug("in UserController.deleteOrderByIdOfUser(userId: " + userId + ", orderId: " + orderId + ")");

        userService.deleteOrderOfUser(userId, orderId);

        LOGGER.info("Deleting order is successful");
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUserById(String userId) {
        LOGGER.debug(String.format("in UserController.deleteUserById(id: %s)", userId));

        userService.deleteUser(userId);

        LOGGER.info("Deleting user is successful");
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Order>> getAllOrdersOfUser(String userId) {
        LOGGER.debug(String.format("in UserController.getAllOrdersOfUser(userId: %s)", userId));

        final List<Order> result = userService.getAllOrdersOfUser(userId);

        LOGGER.info("Retrieving all orders is successful");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        LOGGER.debug("in UserController.getAllUsers()");

        final List<User> userList = userService.getAllUser();

        LOGGER.info("Retrieving all users is successful");
        return ResponseEntity.ok(userList);
    }

    @Override
    public ResponseEntity<Order> getOrderByIdOfUser(String userId, String orderId) {
        LOGGER.debug("in UserController.getOrderByIdOfUser(userId: " + userId + ", orderId: " + orderId + ")");

        final Order result = userService.getOrderOfUser(userId, orderId);

        LOGGER.info("Retrieving order is successful");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<User> getUserById(String userId) {
        LOGGER.debug(String.format("in UserController.getUserById(id: %s)", userId));

        final User result = userService.getUser(userId);

        LOGGER.info("Retrieving user is successful");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> updateUserById(@Valid User body, String userId) {
        LOGGER.debug("in UserController.updateUserById(body: " + body.toString() + ", id:" + userId + ")");

        userService.updateUser(userId, body);

        LOGGER.info("User update is successful");
        return ResponseEntity.ok().build();
    }
}
