package com.nequi.prueba.model.repository;

import com.nequi.prueba.model.entity.ProductoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Long> {

    @Query("SELECT * FROM productos WHERE id_producto != :idProducto AND nombre_producto = :nombreProducto " +
            "AND id_sucursal = :idSucursal")
    Mono<ProductoEntity> nombreRepetido(Long idProducto, String nombreProducto, Long idSucursal);

    Mono<ProductoEntity> findByNombreProductoAndIdSucursal(String nombreProducto, Long idSucursal);

    @Query("SELECT p.id_producto, p.nombre_producto, MAX(p.stock_producto) AS stock_producto, " +
            "p.fecha_producto, p.id_sucursal FROM productos p " +
            "INNER JOIN sucursales s ON (s.id_sucursal = p.id_sucursal) " +
            "WHERE s.id_franquicia = :idFranquicia " +
            "GROUP BY p.id_producto, p.nombre_producto, p.fecha_producto, p.id_sucursal " +
            "ORDER BY stock_producto DESC")
    Flux<ProductoEntity> findMaxStockByFranquicia(Long idFranquicia);

}
