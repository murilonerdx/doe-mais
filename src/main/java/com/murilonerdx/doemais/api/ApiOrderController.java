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
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.security.JwtTokenProvider;
import com.murilonerdx.doemais.services.OngService;
import com.murilonerdx.doemais.services.OrderService;
import com.murilonerdx.doemais.services.UserService;

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
    
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private OngService ongService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> listByOng(HttpServletRequest request) {
    	String username = tokenProvider.getUsername(tokenProvider.resolveToken(request));
        Ong ong = ongService.findByUser(userService.findByUsername(username));
        List<Order> listOrder = service.findByOng(ong);
        return ResponseEntity.ok(listOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        OrderDTO orderDTO = service.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/businessProduct/{businessId}/{idProduct}")
    public ResponseEntity<Order> create(@PathVariable Long idProduct, @PathVariable Long businessId, HttpServletRequest request) throws ParseException {
    	String username = tokenProvider.getUsername(tokenProvider.resolveToken(request));
        Userman user = userService.findByUsername(username);
        Order order = service.create(idProduct, businessId, user);

        if (order == null) throw new ResourceNotFoundException("Você não tem permissão para isso");
        return ResponseEntity.ok().body(order);
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
