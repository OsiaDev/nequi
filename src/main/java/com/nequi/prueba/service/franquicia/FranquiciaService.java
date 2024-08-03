package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.model.entity.FranquiciaEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranquiciaService {

    Mono<FranquiciaEntity> save(FranquiciaEntity franquicia);

    Flux<FranquiciaEntity> findAll();

    Mono<FranquiciaEntity> findById(UUID uuidFranquicia);

    Mono<FranquiciaEntity> update(UUID uuidFranquicia, FranquiciaEntity franquicia);

    Mono<Void> deleteById(UUID uuidFranquicia);

}
