package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.product.ProductRequest;
import co.edu.ustavillavicencio.comeya.dto.product.ProductResponse;
import co.edu.ustavillavicencio.comeya.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest req);
    ProductResponse getById(Long id);
    Page<ProductResponse> list(String q, Pageable pageable);
    ProductResponse update(Long id, ProductUpdateRequest req);
    void delete(Long id);
}
