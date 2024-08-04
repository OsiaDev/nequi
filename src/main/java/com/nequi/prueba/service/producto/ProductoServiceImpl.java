package com.nequi.prueba.service.producto;

import com.nequi.prueba.exception.CustomException;
import com.nequi.prueba.model.dto.NombreProductoRequestDTO;
import com.nequi.prueba.model.dto.ProductoDTO;
import com.nequi.prueba.model.dto.StockProductoRequestDTO;
import com.nequi.prueba.model.entity.ProductoEntity;
import com.nequi.prueba.model.repository.ProductoRepository;
import com.nequi.prueba.service.franquicia.FranquiciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private static final String NOMBRE_DUPLICADO = "El nombre del producto ya está en uso";

    private static final String NOT_FOUNT = "No se encuentra el producto con el id: ";

    private static final String STOCK_NEGATIVO = "EL stock del producto no puede finalizar negativo: ";

    private ProductoRepository repository;

    private FranquiciaService franquiciaService;

    @Override
    public Mono<ProductoEntity> save(ProductoDTO productoDTO) {
        Mono<Boolean> existsNameMono = this.repository.findByNombreProductoAndIdSucursal(productoDTO.getNombreProducto(),
                productoDTO.getIdSucursal()).hasElement();
        return existsNameMono.flatMap(existsName -> {
            if (existsName) {
                return Mono.error(this::nombreDuplicadoException);
            } else {
                return this.repository.save(this.crearProducto(productoDTO));
            }
        });
    }

    @Override
    public Flux<ProductoEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Mono<ProductoEntity> findById(Long idProducto) {
        return this.repository.findById(idProducto).switchIfEmpty(Mono.error(this.notFoundException(idProducto.toString())));
    }

    @Override
    public Mono<ProductoEntity> update(Long idProducto, ProductoDTO productoDTO) {
        Mono<Boolean> productoId = this.repository.findById(idProducto).hasElement();
        Mono<Boolean> productRepeatedName = this.repository.nombreRepetido(idProducto, productoDTO.getNombreProducto(),
                productoDTO.getIdSucursal()).hasElement();
        return productoId.flatMap(existsId -> {
            if (existsId) {
                return productRepeatedName.flatMap(existsName -> {
                    if (existsName) {
                        return Mono.error(this::nombreDuplicadoException);
                    } else {
                        return this.repository.save(this.mapProducto(idProducto, productoDTO));
                    }
                });
            } else {
                return Mono.error(this.notFoundException(idProducto.toString()));
            }
        });
    }

    @Override
    public Mono<ProductoEntity> updateNombre(Long idProducto, NombreProductoRequestDTO productoDTO) {
        Mono<ProductoEntity> productoEntityMono = this.repository.findById(idProducto);
        Mono<Boolean> sucursalId = productoEntityMono.hasElement();
        return sucursalId.flatMap(existsId -> {
            if (existsId) {
                return productoEntityMono
                        .flatMap(productoEntity -> this.repository.nombreRepetido(idProducto, productoDTO.getNombreProducto(), productoEntity.getIdSucursal())
                                .hasElement()
                                .flatMap(exists -> {
                                    if (exists) {
                                        return Mono.error(this::nombreDuplicadoException);
                                    } else {
                                        productoEntity.setNombreProducto(productoDTO.getNombreProducto());
                                        return repository.save(productoEntity);
                                    }
                                })
                        );
            } else {
                return Mono.error(this.notFoundException(idProducto.toString()));
            }
        });
    }

    @Override
    public Mono<Void> deleteById(Long idProducto) {
        Mono<Boolean> sucursal = this.repository.findById(idProducto).hasElement();
        return sucursal.flatMap(exists -> exists ? this.repository.deleteById(idProducto) :
                Mono.error(this.notFoundException(idProducto.toString())));
    }

    @Override
    public Mono<ProductoEntity> addStock(Long idProducto, StockProductoRequestDTO productoDTO) {
        Mono<ProductoEntity> productoEntityMono = this.repository.findById(idProducto);
        Mono<Boolean> sucursalId = productoEntityMono.hasElement();
        return sucursalId.flatMap(existP -> {
            if (existP) {
                return productoEntityMono.flatMap(productoEntity -> {
                    int newStock = productoEntity.getStockProducto() + productoDTO.getStockProducto();
                    productoEntity.setStockProducto(newStock);
                    return this.repository.save(productoEntity);
                });
            } else {
                return Mono.error(this.notFoundException(idProducto.toString()));
            }
        });
    }

    @Override
    public Mono<ProductoEntity> removeStock(Long idProducto, StockProductoRequestDTO productoDTO) {
        Mono<ProductoEntity> productoEntityMono = this.repository.findById(idProducto);
        Mono<Boolean> sucursalId = productoEntityMono.hasElement();
        return sucursalId.flatMap(existP -> {
            if (existP) {
                return productoEntityMono.flatMap(productoEntity -> {
                    int newStock = productoEntity.getStockProducto() - productoDTO.getStockProducto();
                    if (newStock < 0) {
                        return Mono.error(this.stockException());
                    } else {
                        productoEntity.setStockProducto(newStock);
                        return this.repository.save(productoEntity);
                    }
                });
            } else {
                return Mono.error(this.notFoundException(idProducto.toString()));
            }
        });
    }

    @Override
    public Flux<ProductoEntity> getMaxStock(Long idFranquicia) {
        return this.franquiciaService.findById(idFranquicia).flatMapMany(franquiciaEntity ->
                this.repository.findMaxStockByFranquicia(idFranquicia));
    }

    private ProductoEntity mapProducto(Long idProducto, ProductoDTO productoDTO) {
        return ProductoEntity.builder().idProducto(idProducto)
                .nombreProducto(productoDTO.getNombreProducto())
                .idSucursal(productoDTO.getIdSucursal())
                .build();
    }

    private CustomException nombreDuplicadoException() {
        return this.createException(HttpStatus.BAD_REQUEST, NOMBRE_DUPLICADO);
    }

    private CustomException notFoundException(String id) {
        return this.createException(HttpStatus.NOT_FOUND, NOT_FOUNT + id);
    }

    private CustomException stockException() {
        return this.createException(HttpStatus.NOT_FOUND, STOCK_NEGATIVO);
    }

    private CustomException createException(HttpStatus status, String message) {
        return new CustomException(status, message);
    }

    private ProductoEntity crearProducto(ProductoDTO producto) {
        return ProductoEntity.builder().nombreProducto(producto.getNombreProducto())
                .idSucursal(producto.getIdSucursal())
                .stockProducto(producto.getStockProducto())
                .build();
    }

}
