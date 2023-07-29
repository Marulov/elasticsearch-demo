package com.marulov.elasticsearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.marulov.elasticsearch.exception.ElasticsearchException;
import com.marulov.elasticsearch.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepository {

    @Value("${elasticsearch.index}")
    private String indexName;
    private final ElasticsearchClient elasticsearchClient;

    public Product save(Product product) {
        product.setCreated(new Date());

        try {
            IndexResponse response = elasticsearchClient.index(i -> i
                    .index(indexName)
                    .id(UUID.randomUUID().toString())
                    .document(product));
            product.setId(response.id());
            log.info("Product with id {} indexed with version {}", product.getId(), response.version());
        } catch (Exception ex) {
            throw new ElasticsearchException("Error save product elasticsearch index: " + ex.getMessage());
        }

        return product;
    }

    public List<Hit<Product>> getAll() {

        SearchResponse<Product> response;
        try {
            response = elasticsearchClient.search(s -> s
                            .index(indexName)
                            .query(q -> q
                                    .matchAll(t -> t)
                            ),
                    Product.class
            );
        } catch (Exception ex) {
            throw new ElasticsearchException("Error getting products elasticsearch index: " + ex.getMessage());
        }

        return response.hits().hits();
    }

    public List<Hit<Product>> getByIdWithSearchQuery(String id) {

        SearchResponse<Product> response;
        try {
            Query byId = MatchQuery.of(m -> m
                    .field("_id")
                    .query(id)
            )._toQuery();

            response = elasticsearchClient.search(s -> s
                            .index(indexName)
                            .query(byId),
                    Product.class
            );
        } catch (Exception ex) {
            throw new ElasticsearchException("Error getting product elasticsearch index: " + ex.getMessage());
        }

        return response.hits().hits();
    }

    public Optional<Product> getById(String id) {

        GetResponse<Product> response;
        try {
            response = elasticsearchClient.get(g -> g
                            .index(indexName)
                            .id(id),
                    Product.class
            );
            if (response.source() != null) response.source().setId(response.id());
        } catch (Exception ex) {
            throw new ElasticsearchException("Error getting product elasticsearch index: " + ex.getMessage());
        }

        return Optional.ofNullable(response.source());
    }

    public boolean update(String id, Product newProduct) {

        UpdateResponse<Product> response;
        try {
            response = elasticsearchClient.update(u -> u
                    .index(indexName)
                    .id(id)
                    .doc(newProduct), Product.class);

        } catch (Exception ex) {
            throw new ElasticsearchException("Error updating product elasticsearch index: " + ex.getMessage());
        }
        return response.result().equals(Result.Updated);
    }

    public boolean delete(String id) {

        DeleteResponse response;
        try {
            response = elasticsearchClient.delete(d -> d
                    .index(indexName)
                    .id(id));

        } catch (Exception ex) {
            throw new ElasticsearchException("Error deleting product elasticsearch index: " + ex.getMessage());
        }
        return response.result().equals(Result.Deleted);
    }
}