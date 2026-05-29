package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.product.ProductRequest;
import co.edu.ustavillavicencio.comeya.dto.product.ProductResponse;
import co.edu.ustavillavicencio.comeya.dto.product.ProductUpdateRequest;
import co.edu.ustavillavicencio.comeya.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;     

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.create(req));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> list(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(productService.list(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), productService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }
}
