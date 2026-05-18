package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaRequest;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CafeteriaService {
    CafeteriaResponse create(CafeteriaRequest req);
    CafeteriaResponse getById(Long id);
    Page<CafeteriaResponse> search(String q, Pageable pageable);
}
