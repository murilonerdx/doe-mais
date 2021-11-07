package com.murilonerdx.doemais.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OngService ongService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private DoacaoService doacaoService;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public void delete(Long id) {
		Order entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

	public List<Order> findByOng(Long idUsuario) {
		try {
			Ong ong = ongService.findById(idUsuario);
			return repository.findByOng(ong);
		} catch (Exception e) {
			return null;
		}
	}

	public Order create(Order order) {		
		
		doacaoService.create(order);
		
		for (Product produto : order.getProducts()) {
			productService.productAccept(produto);
		}
		return null;
	}
}
