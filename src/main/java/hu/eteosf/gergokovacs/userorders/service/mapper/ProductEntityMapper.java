package hu.eteosf.gergokovacs.userorders.service.mapper;

import java.util.ArrayList;
import java.util.List;

import hu.eteosf.gergokovacs.userorders.model.dto.ProductDto;
import hu.eteosf.gergokovacs.userorders.model.entity.ProductEntity;

public class ProductEntityMapper {
    public static ProductDto toProductDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getProductId(), productEntity.getPrice(), productEntity.getQuantity());
    }

    public static ProductEntity toProductEntity(ProductDto productDto){
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productDto.getProductId());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setQuantity(productDto.getQuantity());

        return productEntity;
    }

    public static List<ProductDto> toListOfProductDtos(List<ProductEntity> list) {
        final List<ProductDto> resultList = new ArrayList<>();
        for (ProductEntity productEntity : list) {
            final ProductDto productDto = toProductDto(productEntity);
            resultList.add(productDto);
        }
        return resultList;
    }

    public static List<ProductEntity> toListOfProductEntities(List<ProductDto> list) {
        final List<ProductEntity> resultList = new ArrayList<>();
        for (ProductDto productDto : list) {
            final ProductEntity productEntity = toProductEntity(productDto);
            resultList.add(productEntity);
        }
        return resultList;
    }
}
