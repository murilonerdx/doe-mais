package com.murilonerdx.doemais.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.dto.BusinessDTO;
import com.murilonerdx.doemais.dto.ProductDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Permission;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.exceptions.DataIntegretyException;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.util.DozerConverter;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Business create(Business business) {
        try {
            String password = business.getUser().getPassword();

            business.setId(null);
            business.getUser().setPassword(new BCryptPasswordEncoder().encode(password));
            business.getUser().getPermissions().add(new Permission(null, "ROLE_BUSINESS"));

            return repository.save(business);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegretyException("Username já existe");
        }
    }

    public List<Business> findAll() {
        return repository.findAll();
    }

    public Business findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public BusinessDTO createProduct(ProductDTO productDTO, Long id) {
        Optional<Userman> user = Optional.ofNullable(
                userRepository.findByUsername(
                        getSessionUser().getUsername()
                ).orElseThrow(
                        () -> new ResourceNotFoundException("Retry login")
                ));

        if (user.get().getPermissions().stream().noneMatch(x -> x.getDescription().equals("ROLE_ONG"))) {
            Product product = DozerConverter
                    .parseObject(productDTO, Product.class);
            product.setId(null);
            product.setStatus(OrderStatus.AVAILABLE);

            Business business = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontada"));
            business.getProducts().add(product);

            Business saved = repository.save(business);

            return DozerConverter.parseObject(saved, BusinessDTO.class);
        }
        return null;
    }

    public void delete(Long id) {
        Business entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(Objects.requireNonNull(entity));
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Userman getSessionUser() {
        return (Userman) getAuthentication().getPrincipal();
    }

}
