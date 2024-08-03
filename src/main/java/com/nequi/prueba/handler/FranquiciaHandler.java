package com.nequi.prueba.handler;

import com.nequi.prueba.common.annotation.RestHandler;
import com.nequi.prueba.model.entity.FranquiciaEntity;
import com.nequi.prueba.service.franquicia.FranquiciaService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@RestHandler
@AllArgsConstructor
public class FranquiciaHandler {

    private final FranquiciaService service;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<FranquiciaEntity> franquicias = this.service.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(franquicias, FranquiciaEntity.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        UUID uuidFranquicia = UUID.fromString(request.pathVariable("uuidFranquicia"));
        Mono<FranquiciaEntity> franquicia = this.service.findById(uuidFranquicia);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(franquicia, FranquiciaEntity.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<FranquiciaEntity> franquicia = request.bodyToMono(FranquiciaEntity.class);
        return franquicia.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.save(f), FranquiciaEntity.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        UUID uuidFranquicia = UUID.fromString(request.pathVariable("uuidFranquicia"));
        Mono<FranquiciaEntity> franquicia = request.bodyToMono(FranquiciaEntity.class);
        return franquicia.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.update(uuidFranquicia, f), FranquiciaEntity.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID uuidFranquicia = UUID.fromString(request.pathVariable("uuidFranquicia"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.deleteById(uuidFranquicia), FranquiciaEntity.class);
    }

}
