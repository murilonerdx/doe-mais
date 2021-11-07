package com.murilonerdx.doemais.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.services.OngService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/ong")
@Api(tags="Endpoint de ongs")
public class ApiOngController {

    @Autowired
    private OngService service;

    @PostMapping
    public ResponseEntity<Ong> create(@RequestBody Ong ong) {
        Ong orgCreated = service.create(ong);
        return ResponseEntity.ok().body(orgCreated);
    }
}
