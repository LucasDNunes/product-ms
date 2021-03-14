package com.productms.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ProductRepository extends JpaRepository<Product, String> {

  List<Product> findByPriceLessThanEqual(Double maxPrice);

  List<Product> findByPriceGreaterThanEqual(Double minPrice);

  List<Product> findByPriceLessThanEqualAndPriceGreaterThanEqual(Double maxPrice, Double minPrice);

  List<Product> findByNameContainingOrDescriptionContaining(String q, String s);

  @Query("select p from Product p where (p.name like %:q% or p.description like %:q%) and p.price <= :maxPrice")
  List<Product> findByNameContainingOrDescriptionContainingAndPriceLessThanEqual(@Param("q") String q, @Param("maxPrice") Double maxPrice);

  @Query("select p from Product p where (p.name like %:q% or p.description like %:q%) and p.price >= :minPrice")
  List<Product> findByNameContainingOrDescriptionContainingAndPriceGreaterThanEqual(@Param("q") String q, @Param("minPrice") Double minPrice);

  @Query("select p from Product p where (p.name like %:q% or p.description like %:q%) and p.price <= :maxPrice and p.price >= :minPrice")
  List<Product> findByNameContainingOrDescriptionContainingAndPriceLessThanEqualAndPriceGreaterThanEqual(@Param("q") String q, @Param("maxPrice")  Double maxPrice, @Param("minPrice")  Double minPrice);
}
