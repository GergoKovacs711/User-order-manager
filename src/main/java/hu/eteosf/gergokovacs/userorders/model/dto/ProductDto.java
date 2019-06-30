package hu.eteosf.gergokovacs.userorders.model.dto;

public class ProductDto {
    private final String productId;
    private final Long price;
    private final Integer quantity;

    public ProductDto(String productId, Long price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDto that = (ProductDto) o;
        return this.productId.equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDto{" + "productId='" + productId + '\'' + ", price=" + price + ", quantity=" + quantity + '}';
    }
}
