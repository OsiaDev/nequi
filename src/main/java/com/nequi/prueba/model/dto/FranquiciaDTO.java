package com.nequi.prueba.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class FranquiciaDTO implements Serializable {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombreFranquicia;

}
