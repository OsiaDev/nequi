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

    @Query("WITH MaxStockOrder AS (SELECT s.id_sucursal, p.id_producto, p.nombre_producto, p.fecha_producto, " +
            "p.stock_producto, s.nombre_sucursal, ROW_NUMBER() OVER (PARTITION BY s.id_sucursal ORDER BY p.stock_producto DESC) AS rnk " +
            "FROM productos p " +
            "JOIN sucursales s ON p.id_sucursal = s.id_sucursal " +
            "WHERE s.id_franquicia = :idFranquicia " +
            ")" +
            "SELECT rp.id_sucursal, rp.nombre_sucursal, rp.id_producto, rp.nombre_producto, rp.stock_producto, rp.fecha_producto " +
            "FROM MaxStockOrder rp " +
            "WHERE rp.rnk = 1 " +
            "ORDER BY rp.nombre_sucursal")
    Flux<ProductoEntity> findMaxStockByFranquicia(Long idFranquicia);

}
