package com.nequi.prueba.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @Column(value = "id_producto")
    private Long idProducto;

    @Column(value = "nombre_producto")
    private String nombreProducto;

    @Builder.Default
    @Column(value = "fecha_producto")
    private LocalDateTime fechaProducto = LocalDateTime.now();

    @Column(value = "id_sucursal")
    private Long idSucursal;

}
