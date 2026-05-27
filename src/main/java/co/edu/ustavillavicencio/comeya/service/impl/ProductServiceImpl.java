package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.product.ProductRequest;
import co.edu.ustavillavicencio.comeya.dto.product.ProductResponse;
import co.edu.ustavillavicencio.comeya.dto.product.ProductUpdateRequest;
import co.edu.ustavillavicencio.comeya.mapper.ProductMapper;
import co.edu.ustavillavicencio.comeya.repository.FoodRepository;
import co.edu.ustavillavicencio.comeya.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FoodRepository foodRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse create(@NonNull ProductRequest req) {
        var f = mapper.toEntity(req);
        foodRepository.save(f);
        return mapper.toResponse(f);
    }

    @Override
    public ProductResponse getById(Long id) {
        return foodRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<ProductResponse> list(String q, Pageable pageable) {
        Page<co.edu.ustavillavicencio.comeya.model.entity.FoodEntity> p = foodRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public ProductResponse update(@NonNull Long id, @NonNull ProductUpdateRequest req) {
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
