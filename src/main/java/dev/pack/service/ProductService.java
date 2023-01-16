package dev.pack.service;

import dev.pack.entity.Product;
import dev.pack.entity.Supplier;
import dev.pack.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service @Slf4j @Transactional
public class ProductService {

    //Dependency Repository
    private final ProductRepository repository;
    private final SupplierService supplierService;
    //Dependency Injection
    @Autowired
    public ProductService(ProductRepository repository, SupplierService supplierService) {
        this.repository = repository;
        this.supplierService = supplierService;
    }

    //Constructor Injection
    public Product addProduct(Product product){
        log.info("Adding new product = "+ product.toString());
        return repository.save(product);
    }

    public Product updateProduct(Product product){
        log.info("Update user = "+ product.toString());
        return repository.save(product);
    }

    public Product findById(Integer id){
        Optional<Product> data = repository.findById(id);
        if(data.isEmpty()){
            log.warn("Id is not exists or there is no data with id ["+id+"]");
            throw new NoSuchElementException("There is no data with id ["+id+"]");
        }
        log.info("Get data with id = [" + id + "]");
        return repository.findById(id).get();
    }

    public Iterable<Product> findAll(){
        return repository.findAll();
    }

    public void deleteProductById(Integer id){
        Optional<Product> dataProduct = repository.findById(id);
        if(dataProduct.isEmpty()){
            log.warn("Id is not exists or there is no data with id ["+id+"]");
            throw new NoSuchElementException("There is no data with id ["+id+"]");
        }
        log.info("Delete product by id : ["+id+"]");
        repository.deleteById(id);
    }

    public void addSupplier(Supplier supplier, Integer productId){
        Product product = findById(productId);
        if(product == null){
            throw new NoSuchElementException("There is no data with id "+productId);
        }
        product.getSuppliers().add(supplier);
        addProduct(product);
    }

    public Product findByProductName(String name){
        return repository.findByProductName(name);
    }

    public List<Product> findByProductNameLike(String name){
        return repository.findByProductNameLike("%"+name+"%");
    }

    public List<Product> findProductByCategory(Integer categoryId){
        return repository.findProductByCategory(categoryId);
    }

    public List<Product> findProductBySupplier(Integer supplierId){
        Supplier supplier = supplierService.findById(supplierId);
        if(supplier == null){
            return new ArrayList<>();
        }
        return repository.findProductBySupplliers(supplier);
    }
}