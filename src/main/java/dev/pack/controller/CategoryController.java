package dev.pack.controller;

import dev.pack.dto.CategoryDto;
import dev.pack.dto.ResponseData;
import dev.pack.dto.SearchData;
import dev.pack.entity.Category;
import dev.pack.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    protected ResponseData<Category> responseData = new ResponseData<>();

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    //TODO : ADD(OK), UPDATE, FIND ALL DATA(OK), FIND DATA BY ID(OK), REMOVE DATA BY ID(OK)

    /**
     *
     * @param categoryDto
     * @param errors
     * @return ResponseData
     */
    @PostMapping(path = "/add-data")
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDto categoryDto, Errors errors){
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryDto, Category.class);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.addCategory(category));

        return ResponseEntity.ok(responseData);
    }

    /**
     *
     * @return Category
     */
    @GetMapping(path = "/find-all")
    public ResponseEntity<Iterable<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     *
     * @param id
     * @return Category
     */
    @GetMapping(path = "/find-category/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    /**
     *
     * @param id
     * @return String
     */
    @DeleteMapping(path = "/remove-data/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id){
        return ResponseEntity.ok("Data ["+id+"] has deleted");
    }

    @PutMapping(path = "/update-data")
    public ResponseEntity<ResponseData<Category>> updateCategory(@Valid @RequestBody CategoryDto categoryDto, Errors errors){
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Category category = modelMapper.map(categoryDto, Category.class);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.addCategory(category));

        return ResponseEntity.ok(responseData);
    }

    //Paging
    @GetMapping("/search/{size}/{page}")
    public ResponseEntity<Iterable<Category>> findByNameWithPaging(@RequestBody SearchData searchData,
                                                                   @PathVariable("size") int size,
                                                                   @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(categoryService.findByNameContains(searchData.getSearchKey(), pageable));
    }

    //Sorting
    @GetMapping(path = "/search/{size}/{page}/{sort}")
    public ResponseEntity<Iterable<Category>> findByNameWithPagingAndSorting(@RequestBody SearchData searchData,
                                                                             @PathVariable("size") int size,
                                                                             @PathVariable("page") int page,
                                                                             @PathVariable("sort") String sort) {
        Pageable pageable = (sort.equalsIgnoreCase("desc"))
                ? PageRequest.of(page, size, Sort.by("id").descending())
                : PageRequest.of(page,size, Sort.by("id"));

        return ResponseEntity.ok(categoryService.findByNameContains(searchData.getSearchKey(), pageable));
    }

    @PostMapping(path = "/save/all")
    public ResponseEntity<ResponseData<Iterable<Category>>> saveBatch(@Valid @RequestBody Category[] categories, Errors errors){
        ResponseData<Iterable<Category>> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
        }

        responseData.setStatus(true);
        responseData.setPayload(categoryService.saveBatch(Arrays.asList(categories)));

        return ResponseEntity.ok(responseData);
    }
}
