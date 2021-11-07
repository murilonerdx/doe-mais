package com.murilonerdx.doemais.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;
    
    @Autowired
    private PermissionService permissionService;

    public Business create(Business business) {
        business.setPassword(AuthenticationService.getPasswordEncoder().encode(business.getPassword()));
        business.getPermissions().add(permissionService.getPermission("ROLE_BUSINESS"));
        return repository.save(business);
    }

    public List<Business> findAll() {
        return repository.findAll();
    }

    public Business findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public Business createProduct(Product product, Long id){
        Business business = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Empresa não encontada"));
        return repository.save(business);
    }

    public void delete(Long id) {
        Business entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(Objects.requireNonNull(entity));
    }

}
