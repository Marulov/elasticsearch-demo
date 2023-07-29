package com.marulov.elasticsearch.service;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.marulov.elasticsearch.dto.ProductCreateRequest;
import com.marulov.elasticsearch.dto.ProductDto;
import com.marulov.elasticsearch.dto.ProductUpdateRequest;
import com.marulov.elasticsearch.exception.ProductNotFoundException;
import com.marulov.elasticsearch.model.Product;
import com.marulov.elasticsearch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto save(ProductCreateRequest request) {
        Product response = productRepository.save(ProductCreateRequest.toProduct(request));

        return ProductDto.convertFrom(response);
    }

    public List<ProductDto> getAll() {
        List<Hit<Product>> response = productRepository.getAll();

        return ProductDto.convertFrom(response);
    }

    public ProductDto getById(String id) {
        Product response = productRepository.getById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        return ProductDto.convertFrom(response);
    }

    public boolean update(String id, ProductUpdateRequest request) {

        return productRepository.update(id, ProductUpdateRequest.toProduct(request));
    }

    public boolean delete(String id) {

        return productRepository.delete(id);
    }
}
