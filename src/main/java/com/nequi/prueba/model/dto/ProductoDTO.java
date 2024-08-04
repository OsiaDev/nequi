package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductoDTO implements Serializable {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombreProducto;

    @Min(value = 0, message = "No puede ingresar un stock negativo")
    private Integer stockProducto;

    @NotBlank(message = "Debe ingresar una sucursal")
    private Long idSucursal;

}
