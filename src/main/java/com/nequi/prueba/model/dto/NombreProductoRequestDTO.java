package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NombreProductoRequestDTO {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombreProducto;

}
