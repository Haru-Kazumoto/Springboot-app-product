package dev.pack.repository;

import dev.pack.entity.Product;
import dev.pack.entity.Supplier;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.nameProduct = :name")
    Product findByProductName(@PathParam("name") String name);

    @Query("SELECT p FROM Product p WHERE p.nameProduct LIKE :name")
    List<Product> findByProductNameLike(@PathParam("name") String name);

    @Query("SELECT c FROM Product c WHERE c.category.id = :categoryId")
    List<Product> findProductByCategory(@PathParam("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p WHERE :supplier MEMBER OF p.suppliers")
    List<Product> findProductBySupplliers(Supplier supplier);
}
