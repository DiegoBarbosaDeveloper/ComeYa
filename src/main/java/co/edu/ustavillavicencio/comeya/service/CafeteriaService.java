package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaRequest;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaResponse;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CafeteriaService {
    CafeteriaResponse create(CafeteriaRequest req);
    CafeteriaResponse getById(Long id);
    Page<CafeteriaResponse> search(String q, Pageable pageable);
    CafeteriaResponse getByName(String name);
    CafeteriaResponse update(Long id, CafeteriaUpdateRequest req);
    void delete(Long id);
}
