package com.murilonerdx.doemais.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.services.OrderService;
import com.murilonerdx.doemais.services.TokenService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/order")
@Api(tags="Endpoint de pedidos")
public class ApiOrderController {

    @Autowired
    private OrderService service;
    
    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Order>> consultarPedidosDaOng(HttpServletRequest request){
        List<Order> listOrderDTO = service.findByOng(tokenService.getIdUsuario(tokenService.extractToken(request)));
        return ResponseEntity.ok(listOrderDTO);
    }
    
    @PostMapping
    public ResponseEntity<Order> cadastrar(@RequestBody Order order) {
    	Order orderCreated = service.create(order);
    	if(orderCreated == null)
    		return ResponseEntity.badRequest().build();
    	return ResponseEntity.ok(orderCreated);
    }
}
