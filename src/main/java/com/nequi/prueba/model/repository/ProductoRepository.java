package com.nequi.prueba.model.repository;

import com.nequi.prueba.model.entity.ProductoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Long> {

    @Query("SELECT * FROM productos WHERE id_producto != :idProducto AND nombre_producto = :nombreProducto " +
            "AND id_sucursal = :idSucursal")
    Mono<ProductoEntity> nombreRepetido(Long idProducto, String nombreProducto, Long idSucursal);

    Mono<ProductoEntity> findByNombreProductoAndIdSucursal(String nombreProducto, Long idSucursal);

}
