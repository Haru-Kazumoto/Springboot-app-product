package dev.pack.controller;

import dev.pack.dto.ResponseData;
import dev.pack.dto.SearchData;
import dev.pack.dto.SupplierDto;
import dev.pack.entity.Supplier;
import dev.pack.service.SupplierService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/suppliers")
public class SupplierController {

    //Dependency Injection
    private final SupplierService supplierService;

    /**
     * How model mapper works :
     *
     * pertama buat objek entity untuk di mapping, lalu object entity diinject dengan modelmapper.
     * Modelmappper ini akan me-mapping field yang serupa dalam object entity dan dto
     * dan akan otomatis memasukan data object entity ke dalam dto dengan field yang serupa.
     */
    private final ModelMapper modelMapper;

    //Field
    protected ResponseData<Supplier> responseData = new ResponseData<>();

    //Constructor dependency
    @Autowired
    public SupplierController(SupplierService supplierService, ModelMapper moderMapper) {
        this.supplierService = supplierService;
        this.modelMapper = moderMapper;
    }

    //Implement controller code

    //ADD
    /**
     *
     * @param supplierDto
     * @param errors
     * @return ResponseData
     */
    @RequestMapping(path = "/create-data", method = RequestMethod.POST)
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierDto supplierDto, Errors errors){
        //if validation error
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.addSupplier(supplier));

        return ResponseEntity.ok(responseData);
    }

    //FIND BY ID
    /**
     *
     * @param id
     * @return ResponseEntity
     */
    @RequestMapping(path = "/find-supplier/{id}", method = RequestMethod.GET)
    public ResponseEntity<Supplier> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(supplierService.findById(id));
    }

    //GET ALL DATA
    /**
     *
     * @return ResponseEntity
     */
    @RequestMapping(path = "/find-all",method = RequestMethod.GET)
    public ResponseEntity<Iterable<Supplier>> findAll(){
        return ResponseEntity.ok(supplierService.findAll());
    }

    //REMOVE DATA BY ID
    /**
     *
     * @param id
     * @return String
     */
    @RequestMapping(path = "/remove-data/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> removeById(@PathVariable("id") Integer id){
        supplierService.removeById(id);
        return ResponseEntity.ok("Data ["+id+"] has deleted");
    }

    //UPDATE DATA
    /**
     *
     * @param supplierDto
     * @param errors
     * @return ResponseData
     */
    @RequestMapping(path = "/update-data",method = RequestMethod.PUT)
    public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierDto supplierDto, Errors errors){
        //check statement
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }

            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.updateSupplierData(supplier));

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/search/byEmail")
    public ResponseEntity<Supplier> findSupplierByEmail(@RequestBody SearchData searchData){
        return ResponseEntity.ok(supplierService.findSupplierByEmail(searchData.getSearchKey()));
    }

    @GetMapping("/search/byName")
    public ResponseEntity<List<Supplier>> findSupplierByNameContains(@RequestBody SearchData searchData){
        return ResponseEntity.ok(supplierService.findSupplierByNameContains(searchData.getSearchKey()));
    }
}
