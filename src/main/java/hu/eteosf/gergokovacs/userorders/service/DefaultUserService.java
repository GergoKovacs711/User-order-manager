package hu.eteosf.gergokovacs.userorders.service;

import static hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity.OrderEntitySatus;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.OrderEntityMapper.toListOfOrderDtos;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.OrderEntityMapper.toOrderDto;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.OrderEntityMapper.toOrderEntity;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.UserEntityMapper.toListOfUserDtos;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.UserEntityMapper.toUserDto;
import static hu.eteosf.gergokovacs.userorders.service.mapper.entity.UserEntityMapper.toUserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.exception.OrderNotFoundException;
import hu.eteosf.gergokovacs.userorders.exception.OrderUpdateException;
import hu.eteosf.gergokovacs.userorders.exception.UserCreationException;
import hu.eteosf.gergokovacs.userorders.exception.UserNotFoundException;
import hu.eteosf.gergokovacs.userorders.exception.UserUpdateException;
import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity;
import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;
import hu.eteosf.gergokovacs.userorders.repository.UserRepository;
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
    public UserDto getUser(String userId) {
        LOGGER.debug("In DefaultUserService.getUser(userId: " + userId + ")");
        final UserEntity fetchedUser = fetchUser(userId);

        LOGGER.info("User has been retrieved");
        return toUserDto(fetchedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        LOGGER.debug("In DefaultUserService.getAllUser()");
        final Iterable<UserEntity> userEntities = repository.findAll();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The user collection: ");
            for (UserEntity userEntity : userEntities) {
                LOGGER.debug(userEntity.toString());
            }
        }
        LOGGER.info("All users have been retrieved");
        return toListOfUserDtos(userEntities);
    }

    @Override
    public void createUser(UserDto userDto) {
        LOGGER.debug("In DefaultUserService.createUser(userDto: " + userDto.toString() + ")");
        if (userDto.getOrders() != null && userDto.getOrders().size() != 0) {
            throw new UserCreationException(
                    "Users must be created before adding and order. The orders field must be null or an empty array");
        }
        final UserEntity userEntity = toUserEntity(userDto);
        LOGGER.debug("The mapped userEntity is : " + userEntity.toString());

        final UserEntity resultEntity = repository.save(userEntity);
        if (LOGGER.isDebugEnabled()) LOGGER.debug("The resultEntity: " + resultEntity.toString());
        LOGGER.info("User has been created");
    }

    @Override
    public void deleteUser(String userId) {
        LOGGER.debug("In DefaultUserService.deleteUser(userId: " + userId + ")");
        final UserEntity entityToDelete = fetchUser(userId);
        repository.delete(entityToDelete);
        LOGGER.info("User has been deleted");
    }

    @Override
    public void updateUser(String userId, User user) {
        LOGGER.debug("In DefaultUserService.updateUser(userId: " + userId + ", user: " + user.toString() + ")");
        if (user.getOrders() != null && user.getOrders().size() != 0) {
            throw new UserUpdateException("The orders field must be null or an empty array");
        }
        final UserEntity fetchedUser = fetchUser(userId);
        final UserEntity resultEntity = updateUserEntity(user, fetchedUser);
        LOGGER.debug("The updated entity: " + resultEntity.toString());

        repository.save(resultEntity);
        LOGGER.info("User has been updated");
    }

    @Override
    public List<OrderDto> getAllOrdersOfUser(String userId) {
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
        return toListOfOrderDtos(orderEntities);
    }

    @Override
    public void createOrderOfUser(String userId, OrderDto orderDto) {
        LOGGER.debug("In DefaultUserService.createOrderOfUser(userId: " + userId + ", orderDto: " + orderDto.toString() + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final OrderEntity newOrder = toOrderEntity(orderDto);

        newOrder.setOrderStatus(OrderEntitySatus.RECEIVED);
        fetchedUser.addOrder(newOrder);
        repository.save(fetchedUser);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("The updated user's orders: " + fetchedUser.getOrders().toString());
        LOGGER.info("The order has been added");
    }

    @Override
    public OrderDto getOrderOfUser(String userId, String orderId) {
        LOGGER.debug("In DefaultUserService.getOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        final OrderEntity resultOrder = processList(orderEntities, (OrderEntity entity) -> {
            if (entity.getOrderId().equals(orderId)) return entity;
            return null;
        });
        if (resultOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        return toOrderDto(resultOrder);
    }

    @Override
    public void deleteOrderOfUser(String userId, String orderId) {
        LOGGER.debug("In DefaultUserService.deleteOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        final OrderEntity resultOrder = processList(orderEntities, (OrderEntity entity) -> {
            if (entity.getOrderId().equals(orderId)) return entity;
            return null;
        });
        if (resultOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        if (resultOrder.getOrderStatus() != OrderEntitySatus.RECEIVED) {
            throw new OrderUpdateException("Only orders with 'received' status can be updated");
        }
        fetchedUser.removeOrder(resultOrder);
        repository.save(fetchedUser);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("The fetched user after the delete: " + fetchedUser.toString());
        LOGGER.info("The order has been deleted");
    }

    @Override
    public void updateOrderOfUser(String userId, String orderId, OrderDto orderDto) {
        LOGGER.debug("In DefaultUserService.deleteOrderOfUser(userId: " +
                        userId + ", orderId: " + orderId + ", orderDto:" + orderDto.toString() + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();

        final OrderEntity resultOrder = processList(orderEntities, (OrderEntity entity) -> {
            if (entity.getOrderId().equals(orderId)) return entity;
            return null;
        });
        if (resultOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        if (resultOrder.getOrderStatus() != OrderEntitySatus.RECEIVED) {
            throw new OrderUpdateException("Only orders with 'recieved' status can be updated");
        }
        fetchedUser.removeOrder(resultOrder);
        repository.save(fetchedUser);

        final OrderEntity newOrder = toOrderEntity(orderDto);
        newOrder.setOrderStatus(OrderEntitySatus.RECEIVED);
        fetchedUser.addOrder(newOrder);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("The fetched user after the delete: " + fetchedUser.toString());
        LOGGER.info("The order has been deleted");
    }

    /**
     * Fetches a user from the repository.
     *
     * @param userId the ID used to find the user
     * @return the found user
     */
    private UserEntity fetchUser(String userId) {
        final Optional<UserEntity> user = repository.findByUserId(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("No user found by the ID: " + userId);
        }
        if (LOGGER.isDebugEnabled()) LOGGER.debug("The retrieved entity: " + user.get().toString());
        return user.get();
    }

    /**
     * Updates the data of the user. It does not update the orders of the user.
     *
     * @param from the data that is used to update the UserEntity
     * @param to   the UserEntity to be updated
     * @return the updated UserEntity
     */
    private UserEntity updateUserEntity(User from, UserEntity to) {
        to.setUserId(from.getUserId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setAddress(from.getAddress());
        return to;
    }

    /**
     * Processes through the list with the given lambda, if the result of the lambda is not null then the method returns early.
     *
     * @param list   the list to be processed
     * @param lambda the calculation that has to be done on each of the given items
     * @return the last output of the lambda expression
     */
    private OrderEntity processList(List<OrderEntity> list, OrderEntityLambda lambda) {
        OrderEntity result = null;
        for (OrderEntity item : list) {
            result = lambda.process(item);
            if (result != null) break;
        }
        return result;
    }

    @FunctionalInterface
    interface OrderEntityLambda {
        OrderEntity process(final OrderEntity data);
    }
}