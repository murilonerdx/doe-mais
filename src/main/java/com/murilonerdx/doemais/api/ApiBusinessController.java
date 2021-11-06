package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.BusinessDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.services.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
@Api(tags="Endpoint de empresas")

public class ApiBusinessController {
    @Autowired
    private BusinessService service;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping()
    public ResponseEntity<List<Business>> listAll() {
        List<Business> listBusiness = service.findAll();
        return ResponseEntity.ok(listBusiness);
    }

    @PostMapping
    public ResponseEntity<Business> create(@RequestBody BusinessDTO business) {
        Business obj = service.create(business);
        return ResponseEntity.ok().body(obj);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping("/addProduct/{id}")
    public ResponseEntity<Business> create(@RequestBody Product productDTO, Long id){
        Business obj = service.createProduct(productDTO, id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping("/{id}")
    public ResponseEntity<Business> getById(@PathVariable Long id) {
        Business business = service.findById(id);
        return ResponseEntity.ok().body(business);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @PostMapping("/product/{id}")
    public ResponseEntity<Business> addProduct(@RequestBody Product product, @PathVariable Long id) {
        try {
            Business obj = service.addProduct(product, id);
            return ResponseEntity.ok().body(obj);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Produto com id " + id + " não encontrado");
        }

    }
}
