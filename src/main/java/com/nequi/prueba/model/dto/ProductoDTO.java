package com.nequi.prueba.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductoDTO implements Serializable {

    private String nombreProducto;

    private Integer stockProducto;

    private Long idSucursal;

}
