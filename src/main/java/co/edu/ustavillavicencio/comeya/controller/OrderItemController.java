package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    @PostMapping
    public ResponseEntity<ApiResponse<OrderItemResponse>> create(@Valid@RequestBody OrderItemRequest ordit) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.name(), OrderItem))
    }
    @GetMapping
    @PutMapping
    @DeleteMapping
    public void manage() {

    }
}
