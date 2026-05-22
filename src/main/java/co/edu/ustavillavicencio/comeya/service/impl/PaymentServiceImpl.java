package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;
import co.edu.ustavillavicencio.comeya.mapper.PaymentMapper;
import co.edu.ustavillavicencio.comeya.repository.OrderRepository;
import co.edu.ustavillavicencio.comeya.repository.PaymentRepository;
import co.edu.ustavillavicencio.comeya.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final OrderRepository orderRepository;

    @Override
    public PaymentResponse create(PaymentRequest req) {
        return new PaymentResponse();
    }
    @Override
    public PaymentResponse update(Long id, PaymentUpdateRequest req) {
        var payment = paymentRepository.findById(id).orElseThrow();
        mapper.updateEntityFromRequest(req, payment);
        paymentRepository.save(payment);
        return mapper.toResponse(payment);
    }

    @Override
    public void delete(Long id) {
        var payment = paymentRepository.findById(id).orElseThrow();
        payment.setActive(false);
        paymentRepository.save(payment);
    }}
