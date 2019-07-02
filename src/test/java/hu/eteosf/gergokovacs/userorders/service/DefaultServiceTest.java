package hu.eteosf.gergokovacs.userorders.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hu.eteosf.gergokovacs.userorders.exception.UserCreationException;
import hu.eteosf.gergokovacs.userorders.exception.UserNotFoundException;
import hu.eteosf.gergokovacs.userorders.model.dto.OrderDto;
import hu.eteosf.gergokovacs.userorders.model.dto.ProductDto;
import hu.eteosf.gergokovacs.userorders.model.dto.UserDto;
import hu.eteosf.gergokovacs.userorders.model.entity.UserEntity;
import hu.eteosf.gergokovacs.userorders.repository.UserRepository;

@RunWith(JUnit4.class)
public class DefaultServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new DefaultUserService(userRepository);
    }

    @Test
    public void whenCallingGetAllUser_shouldReturnAllUsers() {
        // Given
        final UserDto userDto1 = new UserDto("1", "first1", "last1", "address1", null);
        final UserDto userDto2 = new UserDto("2", "first2", "last2", "address2", null);
        final UserDto userDto3 = new UserDto("3", "first3", "last3", "address3", null);
        final UserDto userDto4 = new UserDto("4", "first4", "last4", "address4", null);
        final List<UserDto> expectedList = new ArrayList<>();
        expectedList.add(userDto1);
        expectedList.add(userDto2);
        expectedList.add(userDto3);
        expectedList.add(userDto4);

        final UserEntity userEntity1 = new UserEntity("1", "first1", "last1", "address1", null);
        final UserEntity userEntity2 = new UserEntity("2", "first2", "last2", "address2", null);
        final UserEntity userEntity3 = new UserEntity("3", "first3", "last3", "address3", null);
        final UserEntity userEntity4 = new UserEntity("4", "first4", "last4", "address4", null);
        final List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity1);
        userEntityList.add(userEntity2);
        userEntityList.add(userEntity3);
        userEntityList.add(userEntity4);

        Mockito.when(userRepository.findAll()).thenReturn(userEntityList);

        // When
        final List<UserDto> resultList = userService.getAllUsers();

        // Then
        assertThat("Result list and expected list don't match", resultList,
                   IsIterableContainingInAnyOrder.containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void whenUserIsRequestedIndividually_shouldReturnThemUnmodified() {
        // Given
        final String userId1 = "1";
        final String userId2 = "2";
        final String userId3 = "3";
        final String userId4 = "4";
        final UserDto expectedUserDto1 = new UserDto(userId1, "first1", "last1", "address1", null);
        final UserDto expectedUserDto2 = new UserDto(userId2, "first2", "last2", "address2", null);
        final UserDto expectedUserDto3 = new UserDto(userId3, "first3", "last3", "address3", null);
        final UserDto expectedUserDto4 = new UserDto(userId4, "first4", "last4", "address4", null);
        final UserEntity repoUserEntity1 = new UserEntity(userId1, "first1", "last1", "address1", null);
        final UserEntity repoUserEntity2 = new UserEntity(userId2, "first2", "last2", "address2", null);
        final UserEntity repoUserEntity3 = new UserEntity(userId3, "first3", "last3", "address3", null);
        final UserEntity repoUserEntity4 = new UserEntity(userId4, "first4", "last4", "address4", null);

        Mockito.when(userRepository.findByUserId(userId1)).thenReturn(java.util.Optional.of(repoUserEntity1));
        Mockito.when(userRepository.findByUserId(userId2)).thenReturn(java.util.Optional.of(repoUserEntity2));
        Mockito.when(userRepository.findByUserId(userId3)).thenReturn(java.util.Optional.of(repoUserEntity3));
        Mockito.when(userRepository.findByUserId(userId4)).thenReturn(java.util.Optional.of(repoUserEntity4));

        // When
        final UserDto resultUserDto1 = userService.getUser(userId1);
        final UserDto resultUserDto2 = userService.getUser(userId2);
        final UserDto resultUserDto3 = userService.getUser(userId3);
        final UserDto resultUserDto4 = userService.getUser(userId4);

        // Then
        final String assertMessage = "Expected and result UserDto doesn't match";
        assertThat(assertMessage, resultUserDto1, is(expectedUserDto1));
        assertThat(assertMessage, resultUserDto2, is(expectedUserDto2));
        assertThat(assertMessage, resultUserDto3, is(expectedUserDto3));
        assertThat(assertMessage, resultUserDto4, is(expectedUserDto4));
    }

    @Test(expected = UserNotFoundException.class)
    public void whenCallingGetUser_withNonExistentId_shouldThrowException() {
        // Given
        final String userId1 = "1";
        final UserEntity repoUserEntity1 = new UserEntity(userId1, "first1", "last1", "address1", null);
        when(userRepository.findByUserId(userId1)).thenReturn(Optional.empty());

        // When
        final UserDto resultUserDto1 = userService.getUser(userId1);
    }

    @Test
    public void whenCallingCreateUser_shouldCallRepositorySaveWithExpectedParameters() {
        // Given
        final String userId1 = "1";
        final String userId2 = "2";
        final UserDto userDtoToSave1 = new UserDto(userId1, "first1", "last1", "address1", new ArrayList<>());
        final UserDto userDtoToSave2 = new UserDto(userId2, "first2", "last2", "address2", new ArrayList<>());
        final UserEntity expectedEntity1 = new UserEntity(userId1, "first1", "last1", "address1", new ArrayList<>());
        final UserEntity expectedEntity2 = new UserEntity(userId2, "first2", "last2", "address2", new ArrayList<>());

        Mockito.when(userRepository.save(any())).thenReturn(expectedEntity1).thenReturn(expectedEntity2);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        // When
        userService.createUser(userDtoToSave1);
        userService.createUser(userDtoToSave2);

        Mockito.verify(userRepository, times(2)).save(captor.capture());

        // Then
        final List<UserEntity> capturedArguments = captor.getAllValues();
        final String assertMessage = "Expected and captured argument don't match";
        assertThat(assertMessage, capturedArguments.get(0).getId(), is(expectedEntity1.getId()));
        assertThat(assertMessage, capturedArguments.get(0).getUserId(), is(expectedEntity1.getUserId()));
        assertThat(assertMessage, capturedArguments.get(0).getFirstName(), is(expectedEntity1.getFirstName()));
        assertThat(assertMessage, capturedArguments.get(0).getLastName(), is(expectedEntity1.getLastName()));
        assertThat(assertMessage, capturedArguments.get(0).getOrders(), is(expectedEntity1.getOrders()));
        assertThat(assertMessage, capturedArguments.get(0).getAddress(), is(expectedEntity1.getAddress()));
        assertThat(assertMessage, capturedArguments.get(1).getId(), is(expectedEntity2.getId()));
        assertThat(assertMessage, capturedArguments.get(1).getUserId(), is(expectedEntity2.getUserId()));
        assertThat(assertMessage, capturedArguments.get(1).getFirstName(), is(expectedEntity2.getFirstName()));
        assertThat(assertMessage, capturedArguments.get(1).getLastName(), is(expectedEntity2.getLastName()));
        assertThat(assertMessage, capturedArguments.get(1).getOrders(), is(expectedEntity2.getOrders()));
        assertThat(assertMessage, capturedArguments.get(1).getAddress(), is(expectedEntity2.getAddress()));
    }

    @Test
    public void whenCallingCreateUser_givenUserDtoWithNullReferenceAsOrdersList_shouldNotThrowException() {
        // Given
        final String userId1 = "1";
        final UserDto userDtoToSave1 = new UserDto(userId1, "first1", "last1", "address1", null);
        final UserEntity expectedEntity1 = new UserEntity(userId1, "first1", "last1", "address1", new ArrayList<>());

        Mockito.when(userRepository.save(any())).thenReturn(expectedEntity1);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        // When
        userService.createUser(userDtoToSave1);

        Mockito.verify(userRepository, times(1)).save(captor.capture());

        // Then
        final List<UserEntity> capturedArguments = captor.getAllValues();
        final String assertMessage = "Expected and captured argument don't match";
        assertThat(assertMessage, capturedArguments.get(0).getId(), is(expectedEntity1.getId()));
        assertThat(assertMessage, capturedArguments.get(0).getUserId(), is(expectedEntity1.getUserId()));
        assertThat(assertMessage, capturedArguments.get(0).getFirstName(), is(expectedEntity1.getFirstName()));
        assertThat(assertMessage, capturedArguments.get(0).getLastName(), is(expectedEntity1.getLastName()));
        assertThat(assertMessage, capturedArguments.get(0).getOrders(), is(expectedEntity1.getOrders()));
        assertThat(assertMessage, capturedArguments.get(0).getAddress(), is(expectedEntity1.getAddress()));
    }

    @Test(expected = UserCreationException.class)
    public void whenCallingCreateUser_givenUserDtoWithNonNullAndNotEmptyOrdersList_shouldNotThrowUserCreationException(){
        // Given
        final String userId1 = "1";
        final List<ProductDto> productDtoList = new ArrayList<>();
        final List<OrderDto> orderDtoList = new ArrayList<>();

        final ProductDto productDto = new ProductDto("dummyProductId", 1000L, 1000);
        final OrderDto orderDto = new OrderDto("dummyOrderId", OrderDto.OrderDtoStatus.RECEIVED, productDtoList);

        productDtoList.add(productDto);
        orderDtoList.add(orderDto);

        final UserDto userDtoToSave1 = new UserDto(userId1, "first1", "last1", "address1", orderDtoList);
        final UserEntity expectedEntity1 = new UserEntity(userId1, "first1", "last1", "address1", new ArrayList<>());

        Mockito.when(userRepository.save(any())).thenReturn(expectedEntity1);

        // When
        userService.createUser(userDtoToSave1);
    }

    @Test
    public void whenCallingDeleteUser_shouldCallRepositoryDeleteWithExpectedParameters() {
        // Given
        final String userId1 = "1";
        final String userId2 = "2";
        final UserEntity expectedEntity1 = new UserEntity(userId1, "first1", "last1", "address1", new ArrayList<>());
        final UserEntity expectedEntity2 = new UserEntity(userId2, "first2", "last2", "address2", new ArrayList<>());

        Mockito.when(userRepository.findByUserId(userId1)).thenReturn(Optional.of(expectedEntity1));
        Mockito.when(userRepository.findByUserId(userId2)).thenReturn(Optional.of(expectedEntity2));

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        // When
        userService.deleteUser(userId1);
        userService.deleteUser(userId2);

        Mockito.verify(userRepository, times(2)).delete(captor.capture());

        // Then
        final List<UserEntity> capturedArguments = captor.getAllValues();
        final String assertMessage = "Expected and captured argument don't match";
        assertThat(assertMessage, capturedArguments.get(0).getId(), is(expectedEntity1.getId()));
        assertThat(assertMessage, capturedArguments.get(0).getUserId(), is(expectedEntity1.getUserId()));
        assertThat(assertMessage, capturedArguments.get(0).getFirstName(), is(expectedEntity1.getFirstName()));
        assertThat(assertMessage, capturedArguments.get(0).getLastName(), is(expectedEntity1.getLastName()));
        assertThat(assertMessage, capturedArguments.get(0).getOrders(), is(expectedEntity1.getOrders()));
        assertThat(assertMessage, capturedArguments.get(0).getAddress(), is(expectedEntity1.getAddress()));
        assertThat(assertMessage, capturedArguments.get(1).getId(), is(expectedEntity2.getId()));
        assertThat(assertMessage, capturedArguments.get(1).getUserId(), is(expectedEntity2.getUserId()));
        assertThat(assertMessage, capturedArguments.get(1).getFirstName(), is(expectedEntity2.getFirstName()));
        assertThat(assertMessage, capturedArguments.get(1).getLastName(), is(expectedEntity2.getLastName()));
        assertThat(assertMessage, capturedArguments.get(1).getOrders(), is(expectedEntity2.getOrders()));
        assertThat(assertMessage, capturedArguments.get(1).getAddress(), is(expectedEntity2.getAddress()));
    }

    @Test(expected = UserNotFoundException.class)
    public void whenCallingDeleteUser_withNonExistentUserId_shouldThrowUserNotFoundException() {
        // Given
        final String userId1 = "1";
        final UserEntity expectedEntity1 = new UserEntity(userId1, "first1", "last1", "address1", new ArrayList<>());

        Mockito.when(userRepository.findByUserId(userId1)).thenReturn(Optional.empty());

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        // When
        userService.deleteUser(userId1);
    }
}
