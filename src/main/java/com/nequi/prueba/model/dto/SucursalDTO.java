package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class SucursalDTO implements Serializable {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombreSucursal;

    @NotNull(message = "Debe ingresar una franquicia")
    private Long idFranquicia;

}
