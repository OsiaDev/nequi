package com.nequi.prueba.service.franquicia;

import com.nequi.prueba.model.entity.FranquiciaEntity;
import com.nequi.prueba.model.repository.FranquiciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private FranquiciaRepository repository;

    @Override
    public Mono<FranquiciaEntity> save(FranquiciaEntity franquicia) {
        Mono<Boolean> existsNameMono = this.repository.findByNombreFranquicia(franquicia.getNombreFranquicia()).hasElement();
        return existsNameMono.flatMap(existsName -> {
            if (existsName) {
                return Mono.error(new Exception("El nombre de la franquicia ya está en uso"));
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
        return this.repository.findById(idFranquicia).switchIfEmpty(Mono.error(new NoSuchElementException(idFranquicia.toString())));
    }

    @Override
    public Mono<FranquiciaEntity> update(Long idFranquicia, FranquiciaEntity franquicia) {
        Mono<Boolean> franquiciaUuid = this.repository.findById(idFranquicia).hasElement();
        Mono<Boolean> productRepeatedName = this.repository.nombreRepetido(idFranquicia, franquicia.getNombreFranquicia()).hasElement();
        return franquiciaUuid.flatMap(
                existsId -> existsId ?
                        productRepeatedName.flatMap(existsName -> existsName ? Mono.error(new Exception("Nombre de Francia ya existe"))
                                : this.repository.save(this.mapFranquicia(idFranquicia, franquicia.getNombreFranquicia())))
                        : Mono.error(new Exception("product not found")));
    }

    @Override
    public Mono<Void> deleteById(Long idFranquicia) {
        Mono<Boolean> franquicia = this.repository.findById(idFranquicia).hasElement();
        return franquicia.flatMap(exists -> exists ? this.repository.deleteById(idFranquicia) : Mono.error(new Exception("No se encuentra la franquicia con el id: " + franquicia)));
    }

    private FranquiciaEntity mapFranquicia(Long idFranquicia, String nombreFranquicia) {
        FranquiciaEntity franquicia = new FranquiciaEntity();
        franquicia.setIdFranquicia(idFranquicia);
        franquicia.setNombreFranquicia(nombreFranquicia);
        return franquicia;
    }

}
