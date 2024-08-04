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
@Table(name = "sucursales")
public class SucursalEntity {

    @Id
    @Column(value = "id_sucursal")
    private Long idSucursal;

    @Column(value = "nombre_sucursal")
    private String nombreSucursal;

    @Builder.Default
    @Column(value = "fecha_sucursal")
    private LocalDateTime fechaSucursal = LocalDateTime.now();

    @Column(value = "id_franquicia")
    private Long idFranquicia;

}
