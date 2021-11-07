package com.murilonerdx.doemais.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Permission;
import com.murilonerdx.doemais.repository.PermissionRepository;

@Service
public class PermissionService {
	
	@Autowired
	private PermissionRepository repository;
	
	public Permission getPermission(String description) {
		Optional<Permission> permission = repository.findByDescription(description);
		if(permission.isEmpty())
			return repository.save(new Permission(null, description));
		return permission.get();
	}

}
