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
@Table(name = "franquicias")
public class FranquiciaEntity {

    @Id
    @Column(value = "id_franquicia")
    private Long idFranquicia;

    @Column(value = "nombre_franquicia")
    private String nombreFranquicia;

    @Builder.Default
    @Column(value = "fecha_franquicia")
    private LocalDateTime fechaFranquicia = LocalDateTime.now();

}
