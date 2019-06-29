package hu.eteosf.gergokovacs.userorders.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.api.UsersApi;
import io.swagger.model.Order;
import io.swagger.model.User;

@RestController
public class UserController implements UsersApi {
    @Override
    public ResponseEntity<Void> createOrderById(@Valid Order body, String userId, String orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createOrderOfUser(@Valid Order body, String userId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createUser(@Valid User body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOrderByIdOfUser(String userId, String orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUserById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> getAllOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> getAllUsers() {
        return null;
    }

    @Override
    public ResponseEntity<Void> getOrderByIdOfUser(String userId, String orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> getUserById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateUserById(@Valid User body, String id) {
        return null;
    }
}
