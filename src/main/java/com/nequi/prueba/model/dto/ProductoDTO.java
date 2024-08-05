package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductoDTO implements Serializable {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombreProducto;

    @Min(value = 0, message = "No puede ingresar un stock negativo")
    @NotNull(message = "Debe ingresar un stock mayor a 0")
    private Integer stockProducto;

    @NotNull(message = "Debe ingresar una sucursal")
    private Long idSucursal;

}
