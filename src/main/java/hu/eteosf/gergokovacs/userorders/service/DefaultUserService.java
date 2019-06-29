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
    public User getUser(String userId) {
        LOGGER.debug("In getUser(userId: " + userId + ")");
        final Optional<UserEntity> userEntityOptional = repository.findByUserId(userId);

        if (!userEntityOptional.isPresent()) {
            // TODO: exception handling
            throw new RuntimeException("No user found by the ID: " + userId);
        }

        LOGGER.debug("The retrieved entity: " + userEntityOptional.get().toString());
        LOGGER.info("User has been retrieved");
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
    public void deleteUser(String userId) {
        LOGGER.debug("In deleteUser(userId: " + userId + ")");
        repository.deleteByUserId(userId);
        LOGGER.info("User has been deleted");
    }

    @Override
    public void updateUser(String userId, User user) {
        LOGGER.debug("In updateUser(userId: " + userId + ", user: " + user.toString() + ")");

        // TODO: exception handling
        if(user.getOrders() != null)
            throw new RuntimeException();

        final Optional<UserEntity> userEntityOptional = repository.findByUserId(userId);

        if (!userEntityOptional.isPresent()) {
            // TODO: exception handling
            throw new RuntimeException("No user found by the ID: " + userId);
        }
        final UserEntity resultEntity = updateUserEntity(user, userEntityOptional.get());
        LOGGER.debug("The updated entity: " + resultEntity.toString());

        repository.save(resultEntity);
        LOGGER.info("User has been updated");
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

    /**
     *  Updates the data of the user. It does not update the orders of the user.
     *
     * @param from the data that is used to update the UserEntity
     * @param to the UserEntity to be updated
     * @return the updated UserEntity
     */
    private UserEntity updateUserEntity(User from, UserEntity to) {
        to.setUserId(from.getUserId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setAddress(from.getAddress());
        return to;
    }
}
