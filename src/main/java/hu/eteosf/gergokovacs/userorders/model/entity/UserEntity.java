package hu.eteosf.gergokovacs.userorders.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    public UserEntity() { }

    public UserEntity(String userId, String firstName, String lastName, String address, List<OrderEntity> orders) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.orders = orders;
    }

    public void addOrder(OrderEntity orderEntity){
        orderEntity.setUser(this);
        orders.add(orderEntity);
    }

    public void removeOrder(OrderEntity orderEntity) {
        orders.remove(orderEntity);
    }

    public void updateOrder(OrderEntity oldOrder, OrderEntity newOrder) {
        if(orders.remove(oldOrder)){
            addOrder(newOrder);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderEntity> getOrders() {
        return orders != null ? Collections.unmodifiableList(orders) : null;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        for (OrderEntity orderEntity : orders) {
            orderEntity.setUser(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", userId='" + userId + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
                + '\'' + ", address='" + address + '\'' + ", orders=" + orders + '}';
    }
}
