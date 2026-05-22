package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;

public interface PaymentService {
    PaymentResponse create(PaymentRequest req);
    PaymentResponse update(Long id, PaymentUpdateRequest req);
    void delete(Long id);
}
