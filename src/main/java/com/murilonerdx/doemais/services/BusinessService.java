package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.dto.BusinessDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Permission;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.util.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;

    public Business create(BusinessDTO businessDTO) {
        String password = businessDTO.getUser().getPassword();
        businessDTO.getUser().setPassword(new BCryptPasswordEncoder().encode(password));
        Business business = DozerConverter.parseObject(businessDTO, Business.class);
        business.getUser().getPermissions().add(new Permission(null, "ROLE_BUSINESS"));
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
