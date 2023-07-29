package com.marulov.elasticsearch.dto;

import com.marulov.elasticsearch.model.Product;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        BigDecimal price,
        int stock,
        ProductFeatureDto feature
) {
    public static Product toProduct(ProductUpdateRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .feature(ProductFeatureDto.toProductFeature(request.feature()))
                .build();
    }
}
