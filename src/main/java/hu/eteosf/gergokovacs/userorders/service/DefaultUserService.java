package hu.eteosf.gergokovacs.userorders.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;
import hu.eteosf.gergokovacs.userorders.repository.UserRepository;
import io.swagger.model.Order;
import io.swagger.model.User;

@Service
public class DefaultUserService implements UserService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserRepository repository;

    @Autowired
    public DefaultUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(String id) {
        

    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public void updateUser(String id, User user) {

    }

    @Override
    public List<Order> getAllOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public void createOrderOfUser(String userId, Order order) {

    }

    @Override
    public Order getOrderOfUser(String userId, String orderId) {
        return null;
    }

    @Override
    public void deleteOrderOfUser(String userId, String orderId) {

    }

    @Override
    public void updateOrderOfUser(String userId, String orderId, Order order) {

    }
}
