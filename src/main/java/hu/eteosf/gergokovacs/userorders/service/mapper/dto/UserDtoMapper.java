package hu.eteosf.gergokovacs.userorders.service.mapper.dto;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import io.swagger.model.User;

public class UserDtoMapper {
    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setOrders(OrderDtoMapper.toListOfOrders(userDto.getOrders()));

        return user;
    }

    public static UserDto toUserDto(User user){
        return new UserDto(user.getUserId(), user.getFirstName(), user.getLastName(), user.getAddress(), OrderDtoMapper.toListOfOrderDtos(user.getOrders()));
    }

    public static List<User> toListOfUser(Iterable<UserDto> iterable){
        final List<User> resultList = new ArrayList<>();
        for (UserDto userDto : iterable) {
            final User user = toUser(userDto);
            resultList.add(user);
        }
        return resultList;
    }

    public static List<UserDto> toListOfUserDtos(Iterable<User> iterable){
        final List<UserDto> resultList = new ArrayList<>();
        for (User user : iterable) {
            final UserDto userDto = toUserDto(user);
            resultList.add(userDto);
        }
        return resultList;
    }
}
