package com.nequi.prueba.handler;

import com.nequi.prueba.common.annotation.RestHandler;
import com.nequi.prueba.common.validation.ObjectValidator;
import com.nequi.prueba.model.dto.NombreProductoRequestDTO;
import com.nequi.prueba.model.dto.ProductoDTO;
import com.nequi.prueba.model.entity.ProductoEntity;
import com.nequi.prueba.model.entity.SucursalEntity;
import com.nequi.prueba.service.producto.ProductoService;
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
public class ProductoHandler {

    private final ProductoService service;

    private final ObjectValidator validator;

    private static final String ID = "idProducto";

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<ProductoEntity> productos = this.service.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(productos, ProductoEntity.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long idProducto = Long.valueOf(request.pathVariable(ID));
        Mono<ProductoEntity> producto = this.service.findById(idProducto);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(producto, ProductoEntity.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductoDTO> productoDTO = request.bodyToMono(ProductoDTO.class).doOnNext(this.validator::validate);
        return productoDTO.flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.save(p), ProductoEntity.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long idProducto = Long.valueOf(request.pathVariable(ID));
        Mono<ProductoDTO> productoDTO = request.bodyToMono(ProductoDTO.class).doOnNext(this.validator::validate);
        return productoDTO.flatMap(f -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.update(idProducto, f), ProductoEntity.class));
    }

    public Mono<ServerResponse> updateNombre(ServerRequest request) {
        Long idProducto = Long.valueOf(request.pathVariable(ID));
        Mono<NombreProductoRequestDTO> sucursalDto = request.bodyToMono(NombreProductoRequestDTO.class).doOnNext(this.validator::validate);
        return sucursalDto.flatMap(s -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.updateNombre(idProducto, s), ProductoEntity.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long idProducto = Long.valueOf(request.pathVariable(ID));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(this.service.deleteById(idProducto), ProductoEntity.class);
    }

}
