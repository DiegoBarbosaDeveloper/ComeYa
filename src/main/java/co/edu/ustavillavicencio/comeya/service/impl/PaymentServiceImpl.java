package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.mapper.PaymentMapper;
import co.edu.ustavillavicencio.comeya.repository.PaymentRepository;
import co.edu.ustavillavicencio.comeya.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    @Override
    public PaymentResponse create(PaymentRequest req) {
        return new PaymentResponse();
    }
}
