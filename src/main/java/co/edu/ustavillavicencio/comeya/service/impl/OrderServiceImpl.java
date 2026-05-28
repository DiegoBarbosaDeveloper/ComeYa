package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.order.OrderRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderResponse;
import co.edu.ustavillavicencio.comeya.dto.order.OrderUpdateRequest;
import co.edu.ustavillavicencio.comeya.mapper.OrderMapper;
import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.enums.OrderStatus;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.FoodRepository;
import co.edu.ustavillavicencio.comeya.repository.OrderRepository;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class    OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public OrderResponse create(OrderRequest req, String username) {
        UserEntity user = userRepository.findByName(username).orElseThrow();
        var o = mapper.toEntity(req);
        o.setCustomer(user);

        // Validar y descontar stock
        if (o.getItems() != null) {
            o.getItems().forEach(item -> {
                item.setOrder(o);

                // Descontar stock de cada producto
                item.getFoods().forEach(food -> {
                    FoodEntity product = foodRepository.findById(food.getId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + food.getId()));

                    if (product.getStock() < item.getQuantity()) {
                        throw new RuntimeException("Stock insuficiente para " + product.getName() +
                                ". Disponible: " + product.getStock() + ", solicitado: " + item.getQuantity());
                    }

                    product.setStock(product.getStock() - item.getQuantity());
                    foodRepository.save(product);
                });
            });
        }

        orderRepository.save(o);
        return mapper.toResponse(o);
    }

    @Override
    public OrderResponse getById(@NonNull Long id) {
        return orderRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<OrderResponse> listByCafeteria(@NonNull Long cafeteriaId, @NonNull Pageable pageable) {
        Page<OrderEntity> p = orderRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public OrderResponse update(@NonNull Long id, @NonNull OrderUpdateRequest req) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        mapper.updateEntityFromRequest(req, order);
        orderRepository.save(order);
        return mapper.toResponse(order);
    }

    @Override
    public void delete(@NonNull Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> listAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAll'");
    }

    @Override
    public OrderResponse updateStatus(Long id, String estado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }
}
