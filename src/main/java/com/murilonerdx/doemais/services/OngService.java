package com.murilonerdx.doemais.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.OngRepository;

@Service
public class OngService {

	@Autowired
	private OngRepository repository;

	@Autowired
	private PermissionService permissionService;

	public List<Ong> findAll() {
		return repository.findAll();
	}

	public void delete(Long id) {
		Ong entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

	public Ong create(Ong ong) {
		ong.getPermissions().add(permissionService.getPermission("ROLE_ONG"));
		ong.setPassword(AuthenticationService.getPasswordEncoder().encode(ong.getPassword()));
		return repository.save(ong);
	}

	public Ong findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}
}
