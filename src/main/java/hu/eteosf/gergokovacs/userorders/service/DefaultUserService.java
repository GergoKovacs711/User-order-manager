package hu.eteosf.gergokovacs.userorders.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;
import hu.eteosf.gergokovacs.userorders.repository.UserRepository;
import hu.eteosf.gergokovacs.userorders.service.mapper.UserMapper;
import io.swagger.model.Order;
import io.swagger.model.User;

@Transactional
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
        LOGGER.debug("In getUser(id: " + id + ")");
        final Optional<UserEntity> userEntityOptional = repository.findByUserId(id);

        if (!userEntityOptional.isPresent()) {
            throw new RuntimeException("No user found by the ID: " + id);
        }

        LOGGER.debug("The retrieved entity: " + userEntityOptional.get().toString());
        LOGGER.info("Customer has been retrieved");
        return UserMapper.toUser(userEntityOptional.get());
    }

    @Override
    public List<User> getAllUser() {
        LOGGER.debug("In getAllUser()");

        final Iterable<UserEntity> userEntities = repository.findAll();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The user collection: ");
            for (UserEntity userEntity : userEntities) {
                LOGGER.debug(userEntity.toString());
            }
        }
        LOGGER.info("All users have been retrieved");
        return UserMapper.toListOfUser(userEntities);
    }

    @Override
    public void createUser(User user) {
        LOGGER.debug("In createUser(user: " + user.toString() + ")");
        final UserEntity userEntity = UserMapper.toUserEntity(user);
        LOGGER.debug("The mapped userEntity is : " + userEntity.toString());

        final UserEntity resultEntity = repository.save(userEntity);
        LOGGER.debug("The resultEntity: " + resultEntity.toString());
        LOGGER.info("User has been created");
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
