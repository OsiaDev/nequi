package com.nequi.prueba.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SucursalDTO implements Serializable {

    private String nombreSucursal;

    private Long idFranquicia;

}
