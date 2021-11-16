package com.murilonerdx.doemais.api;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.services.OrderService;
import com.murilonerdx.doemais.util.DozerConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/api/order")
@Api(tags = "Endpoint de pedidos")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header")})
public class ApiOrderController {

    @Autowired
    private OrderService service;

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> listAll() {
        List<OrderDTO> listOrderDTO = DozerConverter.parseListObjects(service.findAll(), OrderDTO.class);
        return ResponseEntity.ok(listOrderDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        OrderDTO orderDTO = service.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/businessProduct/{businessId}/{idProduct}")
    public ResponseEntity<OrderDTO> create(@PathVariable Long idProduct, @PathVariable Long businessId) throws ParseException {
        OrderDTO orderDTO = service.create(idProduct, businessId);

        if (orderDTO == null) throw new ResourceNotFoundException("Você não tem permissão para isso");
        return ResponseEntity.ok().body(orderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/abandonOrder/{id}")
    public ResponseEntity<Void> abandonOrder(@PathVariable Long id, HttpServletRequest request){
        service.abandonOrder(id, request);
        return ResponseEntity.noContent().build();
    }

}
