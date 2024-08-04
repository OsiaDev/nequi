package com.nequi.prueba.service.sucursal;

import com.nequi.prueba.exception.CustomException;
import com.nequi.prueba.model.dto.NombreSucursalRequestDTO;
import com.nequi.prueba.model.dto.SucursalDTO;
import com.nequi.prueba.model.entity.SucursalEntity;
import com.nequi.prueba.model.repository.SucursalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SucursalServiceImpl implements SucursalService {

    private static final String NOMBRE_DUPLICADO = "El nombre de la sucursal ya está en uso";

    private static final String NOT_FOUNT = "No se encuentra la sucursal con el id: ";

    private SucursalRepository repository;

    @Override
    public Mono<SucursalEntity> save(SucursalDTO sucursalDTO) {
        Mono<Boolean> existsNameMono = this.repository.findByNombreSucursalAndIdFranquicia(sucursalDTO.getNombreSucursal(),
                sucursalDTO.getIdFranquicia()).hasElement();
        return existsNameMono.flatMap(existsName -> {
            if (existsName) {
                return Mono.error(this::nombreDuplicadoException);
            } else {
                return this.repository.save(this.crearSucursal(sucursalDTO));
            }
        });
    }

    @Override
    public Flux<SucursalEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Mono<SucursalEntity> findById(Long idSucursal) {
        return this.repository.findById(idSucursal).switchIfEmpty(Mono.error(this.notFoundException(idSucursal.toString())));
    }

    @Override
    public Mono<SucursalEntity> update(Long idSucursal, SucursalDTO sucursalDTO) {
        Mono<Boolean> sucursalId = this.repository.findById(idSucursal).hasElement();
        Mono<Boolean> productRepeatedName = this.repository.nombreRepetido(idSucursal, sucursalDTO.getNombreSucursal(), sucursalDTO.getIdFranquicia()).hasElement();
        return sucursalId.flatMap(existsId -> {
            if (existsId) {
                return productRepeatedName.flatMap(existsName -> {
                    if (existsName) {
                        return Mono.error(this::nombreDuplicadoException);
                    } else {
                        return this.repository.save(this.mapSucursal(idSucursal, sucursalDTO));
                    }
                });
            } else {
                return Mono.error(this.notFoundException(idSucursal.toString()));
            }
        });
    }

    @Override
    public Mono<SucursalEntity> updateNombre(Long idSucursal, NombreSucursalRequestDTO sucursalDTO) {
        Mono<SucursalEntity> sucursalEntityMono = this.repository.findById(idSucursal);
        Mono<Boolean> sucursalId = sucursalEntityMono.hasElement();
        return sucursalId.flatMap(existsId -> sucursalEntityMono
                .flatMap(sucursalEntity -> this.repository.nombreRepetido(idSucursal, sucursalDTO.getNombreSucursal(), sucursalEntity.getIdFranquicia())
                        .hasElement()
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.error(this::nombreDuplicadoException);
                            } else {
                                sucursalEntity.setNombreSucursal(sucursalDTO.getNombreSucursal());
                                return repository.save(sucursalEntity);
                            }
                        })
                )
        );
    }

    @Override
    public Mono<Void> deleteById(Long idSucursal) {
        Mono<Boolean> sucursal = this.repository.findById(idSucursal).hasElement();
        return sucursal.flatMap(exists -> exists ? this.repository.deleteById(idSucursal) : Mono.error(this.notFoundException(idSucursal.toString())));
    }

    private SucursalEntity mapSucursal(Long idSucursal, SucursalDTO sucursalDTO) {
        return SucursalEntity.builder().idSucursal(idSucursal)
                .nombreSucursal(sucursalDTO.getNombreSucursal())
                .idFranquicia(sucursalDTO.getIdFranquicia())
                .build();
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

    private SucursalEntity crearSucursal(SucursalDTO sucursal) {
        return SucursalEntity.builder().nombreSucursal(sucursal.getNombreSucursal())
                .idFranquicia(sucursal.getIdFranquicia())
                .build();
    }

}
