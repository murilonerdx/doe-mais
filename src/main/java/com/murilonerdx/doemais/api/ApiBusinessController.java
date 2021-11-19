package com.murilonerdx.doemais.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.dto.BusinessDTO;
import com.murilonerdx.doemais.dto.ProductDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.services.BusinessService;
import com.murilonerdx.doemais.util.DozerConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/business")
@Api(tags = "Endpoint de empresas")

public class ApiBusinessController {
    @Autowired
    private BusinessService service;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping()
    public ResponseEntity<List<BusinessDTO>> listAll() {
        List<BusinessDTO> listBusiness = DozerConverter.parseListObjects(service.findAll(), BusinessDTO.class);
        return ResponseEntity.ok(listBusiness);
    }

    @PostMapping
    public ResponseEntity<BusinessDTO> create(@RequestBody Business business) {
        Business obj = service.create(business);
        DozerConverter.parseObject(obj, BusinessDTO.class);
        return ResponseEntity.ok().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping("/addProduct/{id}")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        BusinessDTO businessDTO = service.createProduct(productDTO, id);

        if (businessDTO == null) throw new ResourceNotFoundException("Você não tem permissão para isso");
        return ResponseEntity.ok().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{id}")
    public ResponseEntity<BusinessDTO> getById(@PathVariable Long id) {
        BusinessDTO business = DozerConverter.parseObject(service.findById(id), BusinessDTO.class);
        return ResponseEntity.ok().body(business);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}
