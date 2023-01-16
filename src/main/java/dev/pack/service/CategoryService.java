package dev.pack.service;

import dev.pack.entity.Category;
import dev.pack.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category addCategory(Category category){
        return repository.save(category);
    }

    public Category findById(Integer id){
        Optional<Category> categoryId = repository.findById(id);
        if(categoryId.isEmpty()){
            throw new NoSuchElementException("There is no data with id ["+id+"]");
        }
        return categoryId.get();
    }

    public Iterable<Category> findAll(){
        return repository.findAll();
    }

    public void removeById(Integer id){
        Optional<Category> dataProduct = repository.findById(id);
        if(dataProduct.isEmpty()){
            log.warn("Id is not exists or there is no data with id ["+id+"]");
            throw new NoSuchElementException("There is no data with id ["+id+"]");
        }
        log.info("Delete product by id : ["+id+"]");
        repository.deleteById(id);
    }

    public Iterable<Category> findByNameContains(String name, Pageable pageable){
        return repository.findByNameContains(name, pageable);
    }

    public Iterable<Category> saveBatch(Iterable<Category> categories){
        return repository.saveAll(categories);
    }
}
