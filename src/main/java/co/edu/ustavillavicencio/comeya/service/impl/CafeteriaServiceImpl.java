package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaRequest;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaResponse;
import co.edu.ustavillavicencio.comeya.exception.NotFoundException;
import co.edu.ustavillavicencio.comeya.mapper.CafeteriaMapper;
import co.edu.ustavillavicencio.comeya.repository.CafeteriaRepository;
import co.edu.ustavillavicencio.comeya.service.CafeteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeteriaServiceImpl implements CafeteriaService {
    private final CafeteriaRepository cafeteriaRepository;
    private final CafeteriaMapper mapper;

    @Override
    public CafeteriaResponse create(CafeteriaRequest req) {
        var c = mapper.toEntity(req);
        cafeteriaRepository.save(c);
        return mapper.toResponse(c);
    }

    @Override
    public CafeteriaResponse getById(Long id) {
        return cafeteriaRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<CafeteriaResponse> search(String q, Pageable pageable) {
        Page<co.edu.ustavillavicencio.comeya.model.entity.CafeteriaEntity> p = cafeteriaRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public CafeteriaResponse getByName(String name) {
        if (cafeteriaRepository.findByName(name).isEmpty()){
            throw new NotFoundException("Cafeteria Not Found");
        }
        return mapper.toResponse(cafeteriaRepository.findByName(name).get());
    }
}
