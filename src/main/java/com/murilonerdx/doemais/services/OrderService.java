package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.entities.*;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.OngRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.security.JwtTokenProvider;
import com.murilonerdx.doemais.util.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public OrderDTO findById(Long id) {

        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

    public OrderDTO create(List<Long> productsIds, Long id){
        Optional<Userman> user = Optional.ofNullable(
                userRepository.findByUsername(
                        getSessionUser().getUsername()
                ).orElseThrow(
                        () -> new ResourceNotFoundException("Retry login")
                ));

        if (user.get().getPermissions().stream().noneMatch(x -> x.getDescription().equals("ROLE_BUSINESS"))) {
            Ong ong = ongRepository.findByUser(user.get());
            Set<Product> products = new HashSet<>();
            List<Integer> sumTribute = new ArrayList<>();

            Business business = businessRepository.findById(id).get();

            extracted(productsIds, products, business);
            Set<Product> productsAvailable = getCollect(products);

            extractProductSum(sumTribute, productsAvailable);

            int points = getPoints(sumTribute, business);
            business.setPoints(points);

            if (business.getPoints() % 100 == 0) {
                business.setPoints(0.00);
                if (business.getTribute() < 66) {
                    business.setTribute(business.getTribute() + 3);
                }
            }
            Order order = new Order(null, LocalDateTime.now(), productsAvailable, ong);

            return DozerConverter.parseObject(repository.save(order), OrderDTO.class);
        }
        return null;

    }

    private int getPoints(List<Integer> sumTribute, Business business) {
        int points = (int) business.getPoints();
        for (Integer daysDiferente : sumTribute) {
            points = points + (daysDiferente * 5);
        }
        return points;
    }

    private void extractProductSum(List<Integer> sumTribute, Set<Product> productsAvailable) {
        for (Product product : productsAvailable) {
            Period periodo = Period.between(LocalDate.now(), LocalDate.from(product.getDueDate()));
            int diferencesDays = periodo.getDays() + (periodo.getMonths() * 30) + (periodo.getYears() * 365);
            sumTribute.add(diferencesDays);
        }
    }

    private Set<Product> getCollect(Set<Product> products) {
        return products.stream()
                .peek(x -> x.setStatus(OrderStatus.UNAVAILABLE))
                .collect(Collectors.toSet());
    }

    private void extracted(List<Long> productsIds, Set<Product> products, Business business) {
        for (Product product : business.getProducts()) {
            for (Long productId : productsIds) {
                if (product.getId() == productId.longValue()) {
                    products.add(product);
                }
            }
        }
    }

    public void delete(Long id) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Userman getSessionUser() {
        return (Userman) getAuthentication().getPrincipal();
    }

    public void abandonOrder(Long id, HttpServletRequest request){
        String username = tokenProvider.getUsername(tokenProvider.resolveToken(request));
        Ong ong = ongRepository.findByUser(userRepository.findByUsername(username).get());
        Order order = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Pedido não encontrado"));
        if(order.getOng().equals(ong)){
            repository.delete(order);
            DozerConverter.parseObject(order, OrderDTO.class);
        }
    }
}
