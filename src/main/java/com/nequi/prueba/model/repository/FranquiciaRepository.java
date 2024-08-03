package com.nequi.prueba.model.repository;

import com.nequi.prueba.model.entity.FranquiciaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, Long> {

    @Query("SELECT * FROM franquicias WHERE id_franquicia != :idFranquicia AND nombre_franquicia = :nombreFranquicia")
    Mono<FranquiciaEntity> nombreRepetido(Long idFranquicia, String nombreFranquicia);

    Mono<FranquiciaEntity> findByNombreFranquicia(String nombreFranquicia);

}
