package com.nequi.prueba.service.sucursal;

import com.nequi.prueba.model.dto.SucursalDTO;
import com.nequi.prueba.model.entity.SucursalEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalService {

    Mono<SucursalEntity> save(SucursalDTO sucursalDTO);

    Flux<SucursalEntity> findAll();

    Mono<SucursalEntity> findById(Long idSucursal);

    Mono<SucursalEntity> update(Long idSucursal, SucursalDTO sucursalDTO);

    Mono<Void> deleteById(Long idSucursal);

}
