package com.murilonerdx.doemais.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.repository.OngRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.repository.ProductRepository;

@Controller
@RequestMapping
public class OrderController {
    
    @Autowired
    private OngRepository ongRepository;
    
    @Autowired
    private ProductRepository produtoRepository;
    

    @Autowired
    private OrderRepository orderRepository;
    

     	@GetMapping("/pedidos")
        public ModelAndView listAll(Authentication auth) {
            ModelAndView modelAndView = new ModelAndView("pedidos");
            Userman u = (Userman) auth.getPrincipal();
            Ong ong = ongRepository.findByName(u.getUsername());
            List<Order> orders = orderRepository.findByOng(ong);
            modelAndView.addObject("orders", orders);
            return modelAndView;
        }
     	
     	@GetMapping("/giveup/{id}")
     	public String deleteOrder(@PathVariable Long id, Authentication auth, HttpServletRequest http) {
     		Order order = orderRepository.findById(id).get();
			Product product = produtoRepository.findByOrder(order);
			product.setStatus(OrderStatus.AVAILABLE);
			produtoRepository.save(product);
			orderRepository.delete(order);
            return "redirect:/pedidos";
     	} 
     	
     	@GetMapping("/do-request/{id}")
     	public String doRequest(@PathVariable Long id, Authentication auth) {
     	    Order order = new Order();
     		Userman u = (Userman) auth.getPrincipal();
			Ong ong = ongRepository.findByName(u.getUsername());
			order.setOng(ong);
			order.setMoment(LocalDateTime.now());
			Product product = produtoRepository.findById(id).get();
			product.setStatus(OrderStatus.UNAVAILABLE);
			order.setProduct(product);
			produtoRepository.save(product);
			orderRepository.save(order);
     		return "redirect:/pedidos";
     	}
}