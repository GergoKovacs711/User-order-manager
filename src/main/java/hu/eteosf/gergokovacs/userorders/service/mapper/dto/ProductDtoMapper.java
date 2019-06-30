package hu.eteosf.gergokovacs.userorders.service.mapper.dto;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.ProductDto;
import io.swagger.model.Product;

public class ProductDtoMapper {
    public static Product toProduct(ProductDto productDto) {
        final Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return product;
    }

    public static ProductDto toProductDto(Product product){
        return new ProductDto(product.getProductId(), product.getPrice(), product.getQuantity());
    }

    public static List<Product> toListOfProducts(List<ProductDto> list) {
        final List<Product> resultList = new ArrayList<>();
        for (ProductDto productDto : list) {
            final Product product = toProduct(productDto);
            resultList.add(product);
        }
        return resultList;
    }

    public static List<ProductDto> toListOfProductDtos(List<Product> list) {
        final List<ProductDto> resultList = new ArrayList<>();
        for (Product product : list) {
            final ProductDto productDto = toProductDto(product);
            resultList.add(productDto);
        }
        return resultList;
    }
}
