package com.murilonerdx.doemais.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.services.ProductService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/product")
@Api(tags="Endpoint de Produtos")
public class ApiProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> listAll(){
        List<Product> listProducts = service.findAll();
        return ResponseEntity.ok(listProducts);
    }
}
