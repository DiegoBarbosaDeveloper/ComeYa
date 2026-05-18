package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaRequest;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaResponse;
import co.edu.ustavillavicencio.comeya.service.CafeteriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cafeterias")
@RequiredArgsConstructor
public class CafeteriaController {
    private final CafeteriaService cafeteriaService;

    @PostMapping
    public ResponseEntity<ApiResponse<CafeteriaResponse>> create(@Valid @RequestBody CafeteriaRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.name(),cafeteriaService.create(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CafeteriaResponse>>> list(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name() ,cafeteriaService.search(q, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeteriaResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(cafeteriaService.getById(id));
    }
}
