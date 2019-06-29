package hu.eteosf.gergokovacs.userorders.service.mapper;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.entity.ProductEntity;
import io.swagger.model.Product;

public class ProductMapper {
    public static Product toProduct(ProductEntity productEntity) {
        final Product product = new Product();
        product.setProductId(productEntity.getProductId());
        product.setPrice(productEntity.getPrice());
        product.setQuantity(productEntity.getQuantity());

        return product;
    }

    public static ProductEntity toProductEntity(Product product){
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(product.getProductId());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity(product.getQuantity());

        return productEntity;
    }

    public static List<Product> toListOfProduct(List<ProductEntity> list) {
        final List<Product> resultList = new ArrayList<>();
        for (ProductEntity productEntity : list) {
            final Product product = toProduct(productEntity);
            resultList.add(product);
        }
        return resultList;
    }

    public static List<ProductEntity> toListOfProductEntity(List<Product> list) {
        final List<ProductEntity> resultList = new ArrayList<>();
        for (Product product : list) {
            final ProductEntity productEntity = toProductEntity(product);
            resultList.add(productEntity);
        }
        return resultList;
    }
}
