package dev.pack.repository;

import dev.pack.entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier,Integer> {

    Supplier findByEmail(String email);

    List<Supplier> findByNameContains(String name);
}
