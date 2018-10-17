package com.aaxis.microservice.training.demo1.dao;

import com.aaxis.microservice.training.demo1.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findProductsByCategoryId(String categoryId);

    Page<Product> findByIdContainingAndNameContaining(String id, String name, Pageable var1);

    @Query(value = "SELECT * FROM product where category_id = :categoryId order by ?#{#pageable}", countQuery = "SELECT count(1) FROM product", nativeQuery = true)
    Page<Product> findProductsByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

    @Query(value = "select substr(id,3) from product where id like ?1 order by convert(substr(id,3),SIGNED) desc limit 1", nativeQuery = true)
    int getMaxProductId(String id);

    @Query(value = "SELECT p.id,category_id,created_date,name,priority FROM product p INNER JOIN ( SELECT id FROM product USE INDEX (`PRIMARY`) WHERE category_id = :categoryId LIMIT :offSet,:pageSize ) t ON t.id = p.id", nativeQuery = true)
    List<Product> findProductsInPLP(@Param("categoryId") String categoryId, @Param("offSet") int offSet,
            @Param("pageSize") int pageSize);

    @Query(value = "SELECT count(1) FROM product WHERE category_id = :categoryId", nativeQuery = true)
    int findProductsInPLPCount(@Param("categoryId") String categoryId);
}
