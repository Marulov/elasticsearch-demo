package com.marulov.elasticsearch.dto;

import com.marulov.elasticsearch.model.Color;
import com.marulov.elasticsearch.model.ProductFeature;

public record ProductFeatureDto(
        int width,
        int height,
        Color color) {

    public static ProductFeature toProductFeature(ProductFeatureDto dto) {
        return ProductFeature.builder()
                .width(dto.width())
                .height(dto.height())
                .color(dto.color())
                .build();
    }

    public static ProductFeatureDto convertFrom(ProductFeature feature) {
        return new ProductFeatureDto(
                feature.getWidth(),
                feature.getHeight(),
                feature.getColor()
        );
    }
}
