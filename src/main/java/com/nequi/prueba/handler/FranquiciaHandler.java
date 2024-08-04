package com.nequi.prueba.handler;

import com.nequi.prueba.common.annotation.RestHandler;
import com.nequi.prueba.common.validation.ObjectValidator;
import com.nequi.prueba.model.dto.FranquiciaDTO;
import com.nequi.prueba.model.entity.FranquiciaEntity;
import com.nequi.prueba.service.franquicia.FranquiciaService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestHandler
@AllArgsConstructor
public class FranquiciaHandler {

    private final FranquiciaService service;

    private final ObjectValidator validator;

    private static final String ID = "idFranquicia";

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<FranquiciaEntity> franquicias = this.service.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(franquicias, FranquiciaEntity.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long idFranquicia = Long.valueOf(request.pathVariable(ID));
        Mono<FranquiciaEntity> franquicia = this.service.findById(idFranquicia);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(franquicia, FranquiciaEntity.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<FranquiciaDTO> franquiciaDto = request.bodyToMono(FranquiciaDTO.class).doOnNext(this.validator::validate);
        return franquiciaDto.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.save(f), FranquiciaEntity.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long idFranquicia = Long.valueOf(request.pathVariable(ID));
        Mono<FranquiciaDTO> franquiciaDto = request.bodyToMono(FranquiciaDTO.class).doOnNext(this.validator::validate);
        return franquiciaDto.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.update(idFranquicia, f), FranquiciaEntity.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long idFranquicia = Long.valueOf(request.pathVariable(ID));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.deleteById(idFranquicia), FranquiciaEntity.class);
    }

}
