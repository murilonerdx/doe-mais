package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.BusinessDTO;
import com.murilonerdx.doemais.dto.OngDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.services.BusinessService;
import com.murilonerdx.doemais.services.OngService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ong")
@Api(tags="Endpoint de ongs")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header") })
public class ApiOngController {

    @Autowired
    private OngService service;

    @GetMapping()
    public ResponseEntity<List<OngDTO>> listAll() {
        List<OngDTO> listOngs = service.findAll();
        return ResponseEntity.ok(listOngs);
    }

    @PostMapping
    public ResponseEntity<OngDTO> create(@RequestBody OngDTO ongDTO) {
        OngDTO obj = service.create(ongDTO);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
