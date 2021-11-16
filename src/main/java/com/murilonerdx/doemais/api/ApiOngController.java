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

import com.murilonerdx.doemais.dto.OngDTO;
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.services.OngService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/ong")
@Api(tags="Endpoint de ongs")
public class ApiOngController {

    @Autowired
    private OngService service;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @GetMapping()
    public ResponseEntity<List<OngDTO>> listAll() {
        List<OngDTO> listOngs = service.findAll();
        return ResponseEntity.ok(listOngs);
    }

    @PostMapping
    public ResponseEntity<OngDTO> create(@RequestBody Ong ong) {
        service.create(ong);
        return ResponseEntity.ok().build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
