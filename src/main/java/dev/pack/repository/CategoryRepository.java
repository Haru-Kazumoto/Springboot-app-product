package dev.pack.repository;

import dev.pack.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer>,
        CrudRepository<Category, Integer> {

    Page<Category> findByNameContains(String name, Pageable pageable);
}