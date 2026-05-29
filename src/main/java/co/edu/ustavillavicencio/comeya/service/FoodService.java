package co.edu.ustavillavicencio.comeya.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.edu.ustavillavicencio.comeya.dto.food.FoodRequest;
import co.edu.ustavillavicencio.comeya.dto.food.FoodResponse;
import co.edu.ustavillavicencio.comeya.dto.food.FoodUpdateRequest;

public interface FoodService {
    FoodResponse create(FoodRequest req);
    FoodResponse getById(Long id);
    Page<FoodResponse> list(String q, Pageable pageable);
    FoodResponse update(Long id, FoodUpdateRequest req);
    void delete(Long id);
}
