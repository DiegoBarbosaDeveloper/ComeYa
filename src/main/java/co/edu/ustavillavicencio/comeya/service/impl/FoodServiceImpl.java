package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.food.FoodRequest;
import co.edu.ustavillavicencio.comeya.dto.food.FoodResponse;
import co.edu.ustavillavicencio.comeya.dto.food.FoodUpdateRequest;
import co.edu.ustavillavicencio.comeya.mapper.FoodMapper;
import co.edu.ustavillavicencio.comeya.repository.FoodRepository;
import co.edu.ustavillavicencio.comeya.service.FoodService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper mapper;

    @Override
    public FoodResponse create(@NonNull FoodRequest req) {
        var f = mapper.toEntity(req);
        f.setActive(true);
        foodRepository.save(f);
        return mapper.toResponse(f);
    }

    @Override
    public FoodResponse getById(Long id) {
        return foodRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<FoodResponse> list(String q, Pageable pageable) {
        Page<co.edu.ustavillavicencio.comeya.model.entity.FoodEntity> p = foodRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public FoodResponse update(@NonNull Long id, @NonNull FoodUpdateRequest req) {
        var food = foodRepository.findById(id).orElseThrow();
        mapper.updateEntityFromRequest(req, food);
        foodRepository.save(food);
        return mapper.toResponse(food);
    }

    @Override
    public void delete(@NonNull Long id) {
        var food = foodRepository.findById(id).orElseThrow();
        food.setActive(false);
        foodRepository.save(food);
    }
}
