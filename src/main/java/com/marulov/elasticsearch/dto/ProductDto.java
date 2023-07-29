package com.marulov.elasticsearch.dto;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.marulov.elasticsearch.model.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ProductDto(
        String id,
        String name,
        BigDecimal price,
        int stock,
        Date created,
        Date updated,
        ProductFeatureDto feature) {

    public static ProductDto convertFrom(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCreated(),
                product.getUpdated(),
                ProductFeatureDto.convertFrom(product.getFeature())
        );
    }

    public static List<ProductDto> convertFrom(List<Hit<Product>> productResponse) {
        return productResponse.stream()
                .map(product -> {
                    assert product.source() != null;
                    return new ProductDto(
                            product.id(),
                            product.source().getName(),
                            product.source().getPrice(),
                            product.source().getStock(),
                            product.source().getCreated(),
                            product.source().getUpdated(),
                            ProductFeatureDto.convertFrom(product.source().getFeature())
                    );
                }).toList();
    }
}
