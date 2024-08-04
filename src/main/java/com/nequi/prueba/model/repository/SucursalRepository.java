package com.nequi.prueba.model.repository;

import com.nequi.prueba.model.entity.SucursalEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SucursalRepository extends ReactiveCrudRepository<SucursalEntity, Long> {

    @Query("SELECT * FROM sucursales WHERE id_sucursal != :idSucursal AND nombre_sucursal = :nombreSucursal " +
            "AND id_franquicia = :idFranquicia")
    Mono<SucursalEntity> nombreRepetido(Long idSucursal, String nombreSucursal, Long idFranquicia);

    Mono<SucursalEntity> findByNombreSucursalAndIdFranquicia(String nombreSucursal, Long idFranquicia);

}
