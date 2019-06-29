package hu.eteosf.gergokovacs.userorders.service.mapper;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
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

        //TODO: implement it properly
        user.setOrders(null);

        return user;
    }

    public static UserEntity toUserEntity(User user){
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(user.getUserId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setAddress(user.getAddress());

        //TODO: implement it properly
        //userEntity.setOrders(null);

        return userEntity;
    }

    public static List<User> toListOfUser(Iterable<UserEntity> sourceIterable){
        final List<User> resultList = new ArrayList<>();
        for (UserEntity userEntity : sourceIterable) {
            final User user = toUser(userEntity);
            resultList.add(user);
        }
        return resultList;
    }
}
