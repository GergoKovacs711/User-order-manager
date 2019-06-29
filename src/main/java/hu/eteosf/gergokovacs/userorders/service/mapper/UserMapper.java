package hu.eteosf.gergokovacs.userorders.service.mapper;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;
import io.swagger.model.User;

public class UserMapper {
    public static User toUser(UserEntity userEntity) {
        User user = new User();
        user.setUserId(userEntity.getUserId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setAddress(userEntity.getAddress());
        user.setOrders(OrderMapper.toListOfOrders(userEntity.getOrders()));

        return user;
    }

    public static UserEntity toUserEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setAddress(user.getAddress());
        userEntity.setOrders(OrderMapper.toListOfOrderEntities(user.getOrders()));

        return userEntity;
    }

    public static List<User> toListOfUser(Iterable<UserEntity> iterable){
        final List<User> resultList = new ArrayList<>();
        for (UserEntity userEntity : iterable) {
            final User user = toUser(userEntity);
            resultList.add(user);
        }
        return resultList;
    }
}
