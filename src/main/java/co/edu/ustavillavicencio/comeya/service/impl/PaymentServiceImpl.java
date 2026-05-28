package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;
import co.edu.ustavillavicencio.comeya.dto.wompi.WompiTransactionRequest;
import co.edu.ustavillavicencio.comeya.dto.wompi.WompiTransactionResponse;
import co.edu.ustavillavicencio.comeya.dto.wompi.WompiWebhookPayload;
import co.edu.ustavillavicencio.comeya.mapper.PaymentMapper;
import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.entity.PaymentEntity;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentMethod;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentStatus;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentType;
import co.edu.ustavillavicencio.comeya.repository.OrderRepository;
import co.edu.ustavillavicencio.comeya.repository.PaymentRepository;
import co.edu.ustavillavicencio.comeya.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final OrderRepository orderRepository;
    private final RestClient wompiRestClient;

    @Value("${wompi.integrity-key}")
    private String integrityKey;

    @Value("${wompi.webhook-secret}")
    private String webhookSecret;

    @Override
    @Transactional
    public PaymentResponse create(PaymentRequest req) {
        var order = orderRepository.findById(req.getOrderId()).orElseThrow();

        var payment = PaymentEntity.builder()
                .date(OffsetDateTime.now())
                .value(req.getAmount())
                .status(PaymentStatus.PENDING)
                .method(mapMethod(req.getMethod()))
                .active(true)
                .build();
        paymentRepository.save(payment);

        var wompiRequest = new WompiTransactionRequest(
                req.getAmount().multiply(BigDecimal.valueOf(100)).longValue(),
                "COP",
                "COMEYA-" + payment.getId(),
                null
        );

        var wompiResponse = wompiRestClient.post()
                .uri("/transactions")
                .body(wompiRequest)
                .retrieve()
                .body(WompiTransactionResponse.class);

        if (wompiResponse != null && wompiResponse.data() != null) {
            payment.setWompiTransactionId(wompiResponse.data().id());
            paymentRepository.save(payment);
        }

        order.setPayment(payment);
        orderRepository.save(order);

        return mapper.toResponse(payment);
    }

    @Transactional
    public void handleWebhook(WompiWebhookPayload payload) {
        var signature = payload.signature();
        if (signature != null) {
            var expected = generateSignatureChecksum(payload);
            if (!signature.checksum().equals(expected)) {
                throw new SecurityException("Invalid webhook signature");
            }
        }

        var txn = payload.data().transaction();
        if (txn == null) return;

        var payment = paymentRepository.findByWompiTransactionId(txn.id()).orElse(null);
        if (payment == null) return;

        payment.setStatus(mapWompiStatus(txn.status()));
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentResponse> listAll() {
        return paymentRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponse update(Long id, PaymentUpdateRequest req) {
        var payment = paymentRepository.findById(id).orElseThrow();
        mapper.updateEntityFromRequest(req, payment);
        paymentRepository.save(payment);
        return mapper.toResponse(payment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var payment = paymentRepository.findById(id).orElseThrow();
        payment.setActive(false);
        paymentRepository.save(payment);
    }

    private PaymentMethod mapMethod(PaymentType type) {
        return switch (type) {
            case CASH -> PaymentMethod.CASH;
            case TRANSFER -> PaymentMethod.BANK_TRANSFER;
        };
    }

    private PaymentStatus mapWompiStatus(String wompiStatus) {
        return switch (wompiStatus.toUpperCase()) {
            case "APPROVED" -> PaymentStatus.PAID;
            case "DECLINED", "ERROR", "VOIDED" -> PaymentStatus.CANCELLED;
            default -> PaymentStatus.PENDING;
        };
    }

    private String generateSignatureChecksum(WompiWebhookPayload payload) {
        try {
            var mac = Mac.getInstance("HmacSHA256");
            var secretKey = new SecretKeySpec(webhookSecret.getBytes(), "HmacSHA256");
            mac.init(secretKey);

            var data = payload.data().transaction().id()
                    + payload.data().transaction().status()
                    + payload.data().transaction().amountInCents()
                    + payload.timestamp();

            return HexFormat.of().formatHex(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate webhook signature", e);
        }
    }
}
