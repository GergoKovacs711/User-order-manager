package hu.eteosf.gergokovacs.userorders.service;

import static hu.eteosf.gergokovacs.userorders.model.entity.OrderEntity.OrderEntityStatus;
import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderEntityMapper.toListOfOrderDtos;
import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderEntityMapper.toOrderDto;
import static hu.eteosf.gergokovacs.userorders.service.mapper.OrderEntityMapper.toOrderEntity;
import static hu.eteosf.gergokovacs.userorders.service.mapper.UserEntityMapper.toListOfUserDtos;
import static hu.eteosf.gergokovacs.userorders.service.mapper.UserEntityMapper.toUserDto;
import static hu.eteosf.gergokovacs.userorders.service.mapper.UserEntityMapper.toUserEntity;

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
import hu.eteosf.gergokovacs.userorders.service.mapper.ProductEntityMapper;

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
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("The resultEntity: " + resultEntity.toString());
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
    public void updateUser(String userId, UserDto userDto) {
        LOGGER.debug("In DefaultUserService.updateUser(userId: " + userId + ", userDto: " + userDto.toString() + ")");
        if (userDto.getOrders() != null && userDto.getOrders().size() != 0) {
            throw new UserUpdateException("The orders field must be null or an empty array");
        }
        final UserEntity fetchedUser = fetchUser(userId);
        final UserEntity resultEntity = updateUserEntity(userDto, fetchedUser);
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

        newOrder.setOrderStatus(OrderEntityStatus.RECEIVED);
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
        final OrderEntity fetchedOrder = searchForOrderById(orderEntities, orderId);

        if (fetchedOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        return toOrderDto(fetchedOrder);
    }

    @Override
    public void deleteOrderOfUser(String userId, String orderId) {
        LOGGER.debug("In DefaultUserService.deleteOrderOfUser(userId: " + userId + ", orderId: " + orderId + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();
        final OrderEntity fetchedOrder = searchForOrderById(orderEntities, orderId);

        if (fetchedOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        if (fetchedOrder.getOrderStatus() != OrderEntityStatus.RECEIVED) {
            throw new OrderUpdateException("Only orders with 'received' status can be updated");
        }
        fetchedUser.removeOrder(fetchedOrder);
        repository.save(fetchedUser);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("The fetched user after the delete: " + fetchedUser.toString());
        LOGGER.info("The order has been deleted");
    }

    @Override
    public void updateOrderOfUser(String userId, String orderId, OrderDto orderDto) {
        LOGGER.debug(
                "In DefaultUserService.deleteOrderOfUser(userId: " + userId + ", orderId: " + orderId + ", orderDto:" + orderDto.toString()
                        + ")");
        final UserEntity fetchedUser = fetchUser(userId);
        final List<OrderEntity> orderEntities = fetchedUser.getOrders();
        final OrderEntity fetchedOrder = searchForOrderById(orderEntities, orderId);

        if (fetchedOrder == null) throw new OrderNotFoundException("No order found by the ID: " + orderId);
        if (fetchedOrder.getOrderStatus() != OrderEntityStatus.RECEIVED) {
            throw new OrderUpdateException("Only orders with 'received' status can be updated");
        }
        updateOrderEntity(orderDto, fetchedOrder);
        repository.save(fetchedUser);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("The fetched user after the update: " + fetchedUser.toString());
        LOGGER.info("The order has been updated");
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
            throw new UserNotFoundException("No user found by ID: " + userId);
        }
        if (LOGGER.isDebugEnabled()) LOGGER.debug("The retrieved entity: " + user.get().toString());
        return user.get();
    }

    /**
     * Processes through the list and returns the order which has an id that matches with orderId or null if no matching order was found.
     *
     * @param list the list to be processed
     * @return the matching OrderEntity or null
     */
    private OrderEntity searchForOrderById(List<OrderEntity> list, String orderId) {
        OrderEntity result = null;
        for (OrderEntity item : list) {
            if (item.getOrderId().equals(orderId))
                result = item;
        }
        return result;
    }

    /**
     * Updates the data of the user. It does not update the orders of the user.
     *
     * @param from the data that is used to update the UserEntity
     * @param to   the UserEntity to be updated
     * @return the updated UserEntity
     */
    private UserEntity updateUserEntity(UserDto from, UserEntity to) {
        to.setUserId(from.getUserId());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setAddress(from.getAddress());
        return to;
    }

    /**
     * Updates the data of the order. It does not update the status of the order.
     *
     * @param from the data that is used to update the OrderEntity
     * @param to   the UserEntity to be updated
     */
    private void updateOrderEntity(final OrderDto from, final OrderEntity to) {
        to.setOrderId(from.getOrderId());
        to.setProducts(ProductEntityMapper.toListOfProductEntities(from.getProducts()));
    }
}