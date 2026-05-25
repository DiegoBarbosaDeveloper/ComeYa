package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.order.OrderRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderResponse;
import co.edu.ustavillavicencio.comeya.dto.order.StatusUpdateRequest;
import co.edu.ustavillavicencio.comeya.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody OrderRequest req, Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.name(), orderService.create(req, username)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> listAll() {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderService.listAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), orderService.updateStatus(id, request.getEstado())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
