package hu.eteosf.gergokovacs.userorders.model.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orederId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderSatus orderSatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity", cascade = CascadeType.MERGE)
    private List<ProductEntity> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", nullable = false)
    private UserEntity user;

    public OrderEntity() { }

    public OrderEntity(String orederId, OrderSatus orderSatus, List<ProductEntity> products, UserEntity user) {
        this.orederId = orederId;
        this.orderSatus = orderSatus;
        this.products = products;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrederId() {
        return orederId;
    }

    public void setOrederId(String orederId) {
        this.orederId = orederId;
    }

    public OrderSatus getOrderSatus() {
        return orderSatus;
    }

    public void setOrderSatus(OrderSatus orderSatus) {
        this.orderSatus = orderSatus;
    }

    public List<ProductEntity> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
        for (ProductEntity productEntity : products) {
            productEntity.setOrderEntity(this);
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orederId != null ? orederId.hashCode() : 0);
        result = 31 * result + (orderSatus != null ? orderSatus.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderEntity{" + "id=" + id + ", orederId='" + orederId + '\'' + ", orderSatus=" + orderSatus + ", products=" + products
                + '}';
    }

    public enum OrderSatus {
        RECEIVED("RECEIVED"),
        SHIPPED("SHIPPED"),
        DELIVERED("DELIVERED");

        private String value;

        OrderSatus(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }
}
