package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.exception.CustomException;
import com.nequi.prueba.model.dto.FranquiciaDTO;
import com.nequi.prueba.model.entity.FranquiciaEntity;
import com.nequi.prueba.model.repository.FranquiciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private static final String NOMBRE_DUPLICADO = "El nombre de la franquicia ya está en uso";

    private static final String NOT_FOUNT = "No se encuentra la franquicia con el id: ";

    private FranquiciaRepository repository;

    @Override
    public Mono<FranquiciaEntity> save(FranquiciaDTO franquiciaDto) {
        Mono<Boolean> existsNameMono = this.repository.findByNombreFranquicia(franquiciaDto.getNombreFranquicia()).hasElement();
        return existsNameMono.flatMap(existsName -> {
            if (existsName) {
                return Mono.error(this::nombreDuplicadoException);
            } else {
                return this.repository.save(this.crearFranquicia(franquiciaDto));
            }
        });
    }

    @Override
    public Flux<FranquiciaEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Mono<FranquiciaEntity> findById(Long idFranquicia) {
        return this.repository.findById(idFranquicia).switchIfEmpty(Mono.error(this.notFoundException(idFranquicia.toString())));
    }

    @Override
    public Mono<FranquiciaEntity> update(Long idFranquicia, FranquiciaDTO franquiciaDto) {
        Mono<Boolean> franquiciaUuid = this.repository.findById(idFranquicia).hasElement();
        Mono<Boolean> productRepeatedName = this.repository.nombreRepetido(idFranquicia, franquiciaDto.getNombreFranquicia()).hasElement();
        return franquiciaUuid.flatMap(existsId -> {
            if (existsId) {
                return productRepeatedName.flatMap(existsName -> {
                    if (existsName) {
                        return Mono.error(this::nombreDuplicadoException);
                    } else {
                        return this.repository.save(this.mapFranquicia(idFranquicia, franquiciaDto.getNombreFranquicia()));
                    }
                });
            } else {
                return Mono.error(this.notFoundException(idFranquicia.toString()));
            }
        });
    }

    @Override
    public Mono<Void> deleteById(Long idFranquicia) {
        Mono<Boolean> franquicia = this.repository.findById(idFranquicia).hasElement();
        return franquicia.flatMap(exists -> exists ? this.repository.deleteById(idFranquicia) : Mono.error(this.notFoundException(idFranquicia.toString())));
    }

    private FranquiciaEntity mapFranquicia(Long idFranquicia, String nombreFranquicia) {
        return FranquiciaEntity.builder().idFranquicia(idFranquicia).nombreFranquicia(nombreFranquicia).build();
    }

    private CustomException nombreDuplicadoException() {
        return this.createException(HttpStatus.BAD_REQUEST, NOMBRE_DUPLICADO);
    }

    private CustomException notFoundException(String id) {
        return this.createException(HttpStatus.NOT_FOUND, NOT_FOUNT + id);
    }

    private CustomException createException(HttpStatus status, String message) {
        return new CustomException(status, message);
    }

    private FranquiciaEntity crearFranquicia(FranquiciaDTO franquicia) {
        return FranquiciaEntity.builder().nombreFranquicia(franquicia.getNombreFranquicia()).build();
    }

}
