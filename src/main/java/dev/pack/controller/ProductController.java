package dev.pack.controller;

import dev.pack.dto.ResponseData;
import dev.pack.dto.SearchData;
import dev.pack.entity.Product;
import dev.pack.entity.Supplier;
import dev.pack.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //Dependency Injection
    private final ProductService service;

    //Field
    protected ResponseData<Product> responseData = new ResponseData<>();

    //Constructor Dependency Injection
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    //CREATE NEW USER
    /**
     * Complete.
     * @param newProduct
     * @return ResponseData
     */
    @GetMapping("/create-new")
    public ResponseEntity<ResponseData<Product>> createProduct(@Valid @RequestBody Product newProduct, Errors errors){
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(service.addProduct(newProduct));

        return ResponseEntity.ok(responseData);
    }

    //SHOW ALL USER

    /**
     * Complete
     * @return ResponseEntity
     */
    @GetMapping("/show-all")
    public ResponseEntity<Iterable<Product>> showAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    //GET USER BY ID
    /**
     * Complete
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/find-product/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    //DELETE USER BY ID
    /**
     * complete
     * @param id
     * @return String
     */
    @DeleteMapping("/delete-product/{id}")
    public String deleteProductById(@PathVariable("id") Integer id){
        service.deleteProductById(id);
        return "Data ["+id+"]has deleted";
    }

    //UPDATE USER
    /**
     * Complete
     * @param updateUser
     * @return ResponseData
     */
    @PutMapping("/update-product")
    public ResponseEntity<ResponseData<Product>> updateProduct(@Valid @RequestBody Product product, Errors errors){
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(service.updateProduct(product));

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/add-supplier/{id}")
    public ResponseEntity<String> addSupplierToProduct(@RequestBody Supplier supplier, @PathVariable("id") Integer id){
       service.addSupplier(supplier,id);
       return ResponseEntity.ok("Adding supplier to product success!");
    }

    @GetMapping("/search/name")
    public ResponseEntity<Product> getProductByName(@RequestBody SearchData searchData){
        return ResponseEntity.ok(service.findByProductName(searchData.getSearchKey()));
    }

    @GetMapping("/search/nameLike")
    public ResponseEntity<List<Product>> getProductByNameLike(@RequestBody SearchData searchData){
        return ResponseEntity.ok(service.findByProductNameLike(searchData.getSearchKey()));
    }

    @GetMapping("/search/category-id/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("categoryId") Integer categoryId){
        return ResponseEntity.ok(service.findProductByCategory(categoryId));
    }

    @GetMapping("/search/product-by-category/{supplierId}")
    public ResponseEntity<List<Product>> getProductBySupplier(@PathVariable("supplierId") Integer supplierId){
        return ResponseEntity.ok(service.findProductBySupplier(supplierId));
    }
}