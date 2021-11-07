package com.murilonerdx.doemais.services;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.repository.DoacaoRepository;

@Service
public class DoacaoService {

	@Autowired
	private DoacaoRepository repository;

	public void create(@Valid Order order) {
		
		Set<Business> listBusiness = new HashSet<Business>();
		order.getProducts().stream().peek(x -> {
			listBusiness.add(x.getBusiness());
			
		});
		
		System.out.println(listBusiness);
	}
}
