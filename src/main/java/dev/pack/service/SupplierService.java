package dev.pack.service;

import dev.pack.entity.Supplier;
import dev.pack.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class SupplierService {

    private final SupplierRepository repository;

    @Autowired
    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public Supplier addSupplier(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier updateSupplierData(Supplier supplier) {
        return repository.save(supplier);
    }

    public Supplier findById(Integer id) {
        Optional<Supplier> categoryId = repository.findById(id);
        if (categoryId.isEmpty()) {
            throw new NoSuchElementException("There is no data with id [" + id + "]");
        }
        return repository.findById(id).get();
    }

    public Iterable<Supplier> findAll() {
        return repository.findAll();
    }

    public void removeById(Integer id) {
        Optional<Supplier> dataProduct = repository.findById(id);
        if (dataProduct.isEmpty()) {
            log.warn("Id is not exists or there is no data with id [" + id + "]");
            throw new NoSuchElementException("There is no data with id [" + id + "]");
        }
        log.info("Delete product by id : [" + id + "]");
        repository.deleteById(id);
    }

    public Supplier findSupplierByEmail(String email) {
        Optional<Supplier> emailData = Optional.ofNullable(repository.findByEmail(email));
        if (emailData.isEmpty() || emailData.equals("")) {
            throw new NoSuchElementException("[" + email + "] is not exists or empty.");
        }
        return repository.findByEmail(email);
    }

    public List<Supplier> findSupplierByNameContains(String name) {
        return repository.findByNameContains(name);
    }
}