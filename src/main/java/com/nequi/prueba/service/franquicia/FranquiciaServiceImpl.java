package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.exception.CustomException;
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
    public Mono<FranquiciaEntity> save(FranquiciaEntity franquicia) {
        Mono<Boolean> existsNameMono = this.repository.findByNombreFranquicia(franquicia.getNombreFranquicia()).hasElement();
        return existsNameMono.flatMap(existsName -> {
            if (existsName) {
                return Mono.error(this::nombreDuplicadoException);
            } else {
                return this.repository.save(franquicia);
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
    public Mono<FranquiciaEntity> update(Long idFranquicia, FranquiciaEntity franquicia) {
        Mono<Boolean> franquiciaUuid = this.repository.findById(idFranquicia).hasElement();
        Mono<Boolean> productRepeatedName = this.repository.nombreRepetido(idFranquicia, franquicia.getNombreFranquicia()).hasElement();
        return franquiciaUuid.flatMap(existsId -> {
            if (existsId) {
                return productRepeatedName.flatMap(existsName -> {
                    if (existsName) {
                        return Mono.error(this::nombreDuplicadoException);
                    } else {
                        return this.repository.save(this.mapFranquicia(idFranquicia, franquicia.getNombreFranquicia()));
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
        return franquicia.flatMap(exists -> exists ? this.repository.deleteById(idFranquicia) : Mono.error(this.notFoundException(franquicia.toString())));
    }

    private FranquiciaEntity mapFranquicia(Long idFranquicia, String nombreFranquicia) {
        FranquiciaEntity franquicia = new FranquiciaEntity();
        franquicia.setIdFranquicia(idFranquicia);
        franquicia.setNombreFranquicia(nombreFranquicia);
        return franquicia;
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

}
