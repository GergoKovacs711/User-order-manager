package hu.eteosf.gergokovacs.userorders.model.dto;

import java.util.Collections;
import java.util.List;

public class UserDto {
    private final String userId;

    private final String firstName;

    private final String lastName;

    private final String address;

    private final List<OrderDto> orders;

    public UserDto(String userId, String firstName, String lastName, String address, List<OrderDto> orders) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.orders = orders;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderDto> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto that = (UserDto) o;
        return this.userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" + "userId='" + userId + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", address='" + address + '\'' + ", orders=" + orders + '}';
    }
}
