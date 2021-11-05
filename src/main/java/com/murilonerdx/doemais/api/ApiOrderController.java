package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.OrderItem;
import com.murilonerdx.doemais.services.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
@Api(tags="Endpoint de pedidos")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header") })
public class ApiOrderController {

    @Autowired
    private OrderService service;

    @GetMapping()
    public ResponseEntity<List<OrderItem>> listAll(){
        List<OrderItem> listOrderDTO = service.findAll();
        return ResponseEntity.ok(listOrderDTO);
    }

    @PostMapping("/product/{productId}/business/{businessId}")
    public ResponseEntity<OrderItem> orderItem(Long productId, Long businessId){
        OrderItem obj = service.create(productId, businessId);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id){
        OrderDTO orderDTO = service.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
