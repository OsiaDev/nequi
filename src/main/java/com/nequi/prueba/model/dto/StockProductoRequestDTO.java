package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockProductoRequestDTO implements Serializable {

    @Min(value = 1, message = "Debe ingresar un stock mayor a 0")
    private Integer stockProducto;

}
