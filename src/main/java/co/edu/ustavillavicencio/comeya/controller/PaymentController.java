package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;

import co.edu.ustavillavicencio.comeya.dto.payment.PaymentRequest;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;
import co.edu.ustavillavicencio.comeya.dto.wompi.WompiWebhookPayload;
import co.edu.ustavillavicencio.comeya.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse <PaymentResponse>> create(@Valid @RequestBody PaymentRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.name(), paymentService.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> update(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), paymentService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }

    @PostMapping("/webhooks/wompi")
    public ResponseEntity<Void> handleWompiWebhook(@RequestBody WompiWebhookPayload payload) {
        paymentService.handleWebhook(payload);
        return ResponseEntity.ok().build();
    }
}
