package com.nequi.prueba.handler;

import com.nequi.prueba.common.annotation.RestHandler;
import com.nequi.prueba.common.validation.ObjectValidator;
import com.nequi.prueba.model.dto.NombreSucursalRequestDTO;
import com.nequi.prueba.model.dto.SucursalDTO;
import com.nequi.prueba.model.entity.SucursalEntity;
import com.nequi.prueba.service.sucursal.SucursalService;
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
public class SucursalHandler {

    private final SucursalService service;

    private final ObjectValidator validator;

    private static final String ID = "idSucursal";

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<SucursalEntity> sucursales = this.service.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(sucursales, SucursalEntity.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long idSucursal = Long.valueOf(request.pathVariable(ID));
        Mono<SucursalEntity> sucursal = this.service.findById(idSucursal);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(sucursal, SucursalEntity.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<SucursalDTO> sucursalDTO = request.bodyToMono(SucursalDTO.class).doOnNext(this.validator::validate);
        return sucursalDTO.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.save(f), SucursalEntity.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long idSucursal = Long.valueOf(request.pathVariable(ID));
        Mono<SucursalDTO> sucursalDto = request.bodyToMono(SucursalDTO.class).doOnNext(this.validator::validate);
        return sucursalDto.flatMap(s -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.update(idSucursal, s), SucursalEntity.class));
    }

    public Mono<ServerResponse> updateNombre(ServerRequest request) {
        Long idSucursal = Long.valueOf(request.pathVariable(ID));
        Mono<NombreSucursalRequestDTO> sucursalDto = request.bodyToMono(NombreSucursalRequestDTO.class).doOnNext(this.validator::validate);
        return sucursalDto.flatMap(s -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.updateNombre(idSucursal, s), SucursalEntity.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long idSucursal = Long.valueOf(request.pathVariable(ID));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.deleteById(idSucursal), SucursalEntity.class);
    }

}
