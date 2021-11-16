package com.murilonerdx.doemais.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.OngRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.security.JwtTokenProvider;
import com.murilonerdx.doemais.util.DozerConverter;

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

    public OrderDTO create(Long productId, Long id) {
        Optional<Userman> user = Optional.ofNullable(
                userRepository.findByUsername(
                        getSessionUser().getUsername()
                ).orElseThrow(
                        () -> new ResourceNotFoundException("Retry login")
                ));

        if (user.get().getPermissions().stream().noneMatch(x -> x.getDescription().equals("ROLE_BUSINESS"))) {
            Ong ong = ongRepository.findByUser(user.get());
            List<Integer> sumTribute = new ArrayList<>();

            Business business = businessRepository.findById(id).get();

            Product product = extracted(productId, business);
            Product productAvailable = getCollect(Objects.requireNonNull(product));

            extractProductSum(sumTribute, product);

            int points = getPoints(sumTribute, business);
            business.setPoints(points);

            if (business.getPoints() % 100 == 0) {
                business.setPoints(0.00);
                if (business.getTribute() < 66) {
                    business.setTribute(business.getTribute() + 3);
                }
            }
            Order order = new Order(null, LocalDateTime.now(), productAvailable, ong);

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

    private void extractProductSum(List<Integer> sumTribute, Product product) {
        Period periodo = Period.between(LocalDate.now(), LocalDate.from(product.getDueDate()));
        int diferencesDays = periodo.getDays() + (periodo.getMonths() * 30) + (periodo.getYears() * 365);
        sumTribute.add(diferencesDays);
    }

    private Product getCollect(Product product) {
        product.setStatus(OrderStatus.UNAVAILABLE);
        return product;
    }

    private Product extracted(Long productId, Business business) {
        for (Product product : business.getProducts()) {
            if (product.getId() == productId.longValue()) {
                return product;
            }
        }
        return null;
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

    public void abandonOrder(Long id, HttpServletRequest request) {
        String username = tokenProvider.getUsername(tokenProvider.resolveToken(request));
        Ong ong = ongRepository.findByUser(userRepository.findByUsername(username).get());
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        if (order.getOng().equals(ong)) {
            repository.delete(order);
            DozerConverter.parseObject(order, OrderDTO.class);
        }
    }
}
