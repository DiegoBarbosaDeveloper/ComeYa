package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.order.CreateOrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;
import co.edu.ustavillavicencio.comeya.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderItemResponse>> create(@Valid @RequestBody CreateOrderItemRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED.name(), orderItemService.createItem(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderItemService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> update(@PathVariable Long id, @Valid @RequestBody CreateOrderItemRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderItemService.updateItem(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderItemService.delete(id)));
    }
}
