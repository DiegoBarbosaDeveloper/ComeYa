package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;
import co.edu.ustavillavicencio.comeya.dto.wompi.WompiWebhookPayload;

import java.util.List;

public interface PaymentService {
    PaymentResponse create(PaymentRequest req);
    List<PaymentResponse> listAll();
    PaymentResponse update(Long id, PaymentUpdateRequest req);
    void delete(Long id);
    void handleWebhook(WompiWebhookPayload payload);
}
