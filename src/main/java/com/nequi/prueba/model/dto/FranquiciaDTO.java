package com.nequi.prueba.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FranquiciaDTO implements Serializable {

    private UUID uuidFranquicia;

    private String nombreFranquicia;

    private LocalDateTime fechaFranquicia;

}
