package com.nequi.prueba.service.producto;

import com.nequi.prueba.model.dto.ProductoDTO;
import com.nequi.prueba.model.entity.ProductoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Mono<ProductoEntity> save(ProductoDTO productoDTO);

    Flux<ProductoEntity> findAll();

    Mono<ProductoEntity> findById(Long idProducto);

    Mono<ProductoEntity> update(Long idProducto, ProductoDTO productoDTO);

    Mono<Void> deleteById(Long idProducto);

}
