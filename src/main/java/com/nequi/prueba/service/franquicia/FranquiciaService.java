package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.model.dto.FranquiciaDTO;
import com.nequi.prueba.model.entity.FranquiciaEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {

    Mono<FranquiciaEntity> save(FranquiciaDTO franquiciaDto);

    Flux<FranquiciaEntity> findAll();

    Mono<FranquiciaEntity> findById(Long idFranquicia);

    Mono<FranquiciaEntity> update(Long idFranquicia, FranquiciaDTO franquiciaDto);

    Mono<Void> deleteById(Long idFranquicia);

}
