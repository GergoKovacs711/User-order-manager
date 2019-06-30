package hu.eteosf.gergokovacs.userorders.service.mapper.entity;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;

public class UserEntityMapper {
    public static UserDto toUserDto(UserEntity userEntity) {
        return new UserDto(userEntity.getUserId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getAddress(),
                           OrderEntityMapper.toListOfOrderDtos(userEntity.getOrders()));
    }

    public static UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setOrders(OrderEntityMapper.toListOfOrderEntities(userDto.getOrders()));

        return userEntity;
    }

    public static List<UserDto> toListOfUserDtos(Iterable<UserEntity> iterable) {
        final List<UserDto> resultList = new ArrayList<>();
        for (UserEntity userEntity : iterable) {
            final UserDto userDto = toUserDto(userEntity);
            resultList.add(userDto);
        }
        return resultList;
    }

    public static List<UserEntity> toListOfUserEntities(Iterable<UserDto> iterable) {
        final List<UserEntity> resultList = new ArrayList<>();
        for (UserDto userDto : iterable) {
            final UserEntity userEntity = toUserEntity(userDto);
            resultList.add(userEntity);
        }
        return resultList;
    }
}
