package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.food.FoodRequest;
import co.edu.ustavillavicencio.comeya.dto.food.FoodResponse;
import co.edu.ustavillavicencio.comeya.dto.food.FoodUpdateRequest;     

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService productService;

    @PostMapping
    public ResponseEntity<FoodResponse> create(@Valid @RequestBody FoodRequest req) {
        return ResponseEntity.ok(productService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<FoodResponse>> list(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(productService.list(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FoodResponse>> update(@PathVariable Long id, @Valid @RequestBody FoodUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), productService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }
}
