package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.model.entity.FranquiciaEntity;
import com.nequi.prueba.model.repository.FranquiciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private FranquiciaRepository repository;

    @Override
    public Mono<FranquiciaEntity> save(FranquiciaEntity franquicia) {
        return null;
    }

    @Override
    public Flux<FranquiciaEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Mono<FranquiciaEntity> findById(UUID uuidFranquicia) {
        return this.repository.findById(uuidFranquicia);
    }

    @Override
    public Mono<FranquiciaEntity> update(UUID uuidFranquicia, FranquiciaEntity franquicia) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(UUID uuidFranquicia) {
        return this.repository.deleteById(uuidFranquicia);
    }


}
