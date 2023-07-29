package com.marulov.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @JsonProperty("_id")
    private String id;
    private String name;
    private BigDecimal price;
    private int stock;
    private Date created;
    private Date updated;
    private ProductFeature feature;
}
