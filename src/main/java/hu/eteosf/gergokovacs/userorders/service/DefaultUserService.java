package hu.eteosf.gergokovacs.userorders.service;

import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderMapper.toListOfOrders;
import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderMapper.toOrder;
import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderMapper.toOrderEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity;
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
        LOGGER.debug("In DefaultUserService.getUser(userId: " + userId + ")");
        final UserEntity fetchedUser = fetchUser(userId);

        LOGGER.info("User has been retrieved");
        return UserMapper.toUser(fetchedUser);
    }

    @Override
    public List<User> getAllUser() {
        LOGGER.debug("In DefaultUserService.getAllUser()");

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
        LOGGER.debug("In DefaultUserService.createUser(user: " + user.toString() + ")");
        final UserEntity userEntity = UserMapper.toUserEntity(user);
        LOGGER.debug("The mapped userEntity is : " + userEntity.toString());

        final UserEntity resultEntity = repository.save(userEntity);
        LOGGER.debug("The resultEntity: " + resultEntity.toString());
        LOGGER.info("User has been created");
    }

    @Override
    public void deleteUser(String userId) {
        LOGGER.debug("In DefaultUserService.deleteUser(userId: " + userId + ")");
        repository.deleteByUserId(userId);
        LOGGER.info("User has been deleted");
    }

    @Override
    public void updateUser(String userId, User user) {
        LOGGER.debug("In DefaultUserService.updateUser(userId: " + userId + ", user: " + user.toString() + ")");

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
        LOGGER.debug("In DefaultUserService.getAllOrdersOfUser(userId: " + userId + ")");

        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The orderEntities list: ");
            for (OrderEntity OrderEntity : orderEntities) {
                LOGGER.debug(OrderEntity.toString());
            }
        }
        LOGGER.info("All orders have been retrieved");
        return toListOfOrders(orderEntities);
    }

    @Override
    public void createOrderOfUser(String userId, Order order) {
        LOGGER.debug("In DefaultUserService.createOrderOfUser(userId: " + userId + ", order: " + order.toString() +")");

        final UserEntity fetchedUser = fetchUser(userId);
        final OrderEntity orderEntity = toOrderEntity(order);
        orderEntity.setOrderStatus(OrderEntity.OrderSatus.RECEIVED);
        fetchedUser.addOrder(orderEntity);
        repository.save(fetchedUser);

        if(LOGGER.isDebugEnabled())
            LOGGER.debug("The updated user's orders: " + fetchedUser.getOrders().toString());
        LOGGER.info("The order has been added");
    }

    @Override
    public Order getOrderOfUser(String userId, String orderId) {
        LOGGER.debug("In DefaultUserService.getOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");

        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        final OrderEntity resultOrder = processList(orderEntities, (OrderEntity entity) -> {
            if(entity.getOrderId().equals(orderId))
                return entity;
            return null;
        });

        // TODO: exception handling
        if(resultOrder == null)
            throw new RuntimeException();

        return toOrder(resultOrder);
    }

    @Override
    public void deleteOrderOfUser(String userId, String orderId) {
        LOGGER.debug("In DefaultUserService.deleteOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");

        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        final OrderEntity resultOrder = processList(orderEntities, (OrderEntity entity) -> {
            if(entity.getOrderId().equals(orderId) && entity.getOrderStatus() == OrderEntity.OrderSatus.RECEIVED)
                return entity;
            return null;
        });

        // TODO: exception handling
        if(resultOrder == null)
            throw new RuntimeException();

        fetchedUser.removeOrder(resultOrder);
        repository.save(fetchedUser);

        LOGGER.debug("The fetched user after the delete: " + fetchedUser.toString());
        LOGGER.info("The order has been deleted");
    }

    @Override
    public void updateOrderOfUser(String userId, String orderId, Order order) {
        LOGGER.debug("In DefaultUserService.deleteOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");

        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        boolean updateSuccessful = false;
        for (OrderEntity orderEntity : orderEntities) {
            LOGGER.debug(orderEntity.toString());
            if(orderEntity.getOrderId().equals(orderId) && orderEntity.getOrderStatus() == OrderEntity.OrderSatus.RECEIVED) {
                fetchedUser.removeOrder(orderEntity);
                repository.save(fetchedUser);
                updateSuccessful = true;
                break;
            }
        }
        // TODO: exception handling
        if(!updateSuccessful)
            throw new RuntimeException();

        fetchedUser.addOrder(toOrderEntity(order));
        LOGGER.debug("The fetched user after the delete: " + fetchedUser.toString());
        LOGGER.info("The order has been deleted");
    }

    private UserEntity fetchUser(String userId) {
        final Optional<UserEntity> userEntityOptional = repository.findByUserId(userId);

        if (!userEntityOptional.isPresent()) {
            // TODO: exception handling
            throw new RuntimeException("No user found by the ID: " + userId);
        }

        LOGGER.debug("The retrieved entity: " + userEntityOptional.get().toString());
        return userEntityOptional.get();
    }

    /**
     *  Processes through the list with the given lambda, if the result of the lambda is not null then the method returns early.
     *
     * @param list the list to be processed
     * @param lambda the calculation that has to be done on each of the given items
     * @return the last output of the lambda expression
     */
    private OrderEntity processList(List<OrderEntity> list, OrderEntityLambda lambda) {
        OrderEntity result = null;
        for (OrderEntity item : list) {
            result = lambda.process(item);
            if(result != null)
                break;
        }
        return result;
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

    @FunctionalInterface
    interface OrderEntityLambda {
        OrderEntity process(final OrderEntity data);
    }
}
