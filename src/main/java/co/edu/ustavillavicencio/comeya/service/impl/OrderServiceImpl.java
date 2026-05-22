package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.order.OrderRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderResponse;
import co.edu.ustavillavicencio.comeya.mapper.OrderMapper;
import co.edu.ustavillavicencio.comeya.model.enums.OrderStatus;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.OrderRepository;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final UserRepository userRepository;

    @Override
    public OrderResponse create(OrderRequest req, String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        var o = mapper.toEntity(req);
        o.setUser(user);
        if (o.getItems() != null) {
            o.getItems().forEach(item -> item.setOrder(o));
        }
        orderRepository.save(o);
        return mapper.toResponse(o);
    }

    @Override
    public OrderResponse getById(Long id) {
        return orderRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<OrderResponse> listByCafeteria(Long cafeteriaId, Pageable pageable) {
        Page<co.edu.ustavillavicencio.comeya.model.entity.OrderEntity> p = orderRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public List<OrderResponse> listAll() {
        return orderRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateStatus(Long id, String estado) {
        var order = orderRepository.findById(id).orElseThrow();
        OrderStatus status;
        if ("COBRADO".equalsIgnoreCase(estado)) {
            status = OrderStatus.ENTREGADO;
        } else {
            status = OrderStatus.valueOf(estado);
        }
        order.setStatus(status);
        orderRepository.save(order);
        return mapper.toResponse(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
