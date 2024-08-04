package com.nequi.prueba.router;

import com.nequi.prueba.handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductoRouter {

    private static final String PATH = "productos";

    @Bean
    RouterFunction<ServerResponse> productRouterFunction(ProductoHandler productoHandler) {
        return RouterFunctions.route()
                .GET(PATH + "/getAll", productoHandler::getAll)
                .GET(PATH + "/getById/{idProducto}", productoHandler::getById)
                .POST(PATH + "/create", productoHandler::save)
                .PUT(PATH + "/update/{idProducto}", productoHandler::update)
                .DELETE(PATH + "/deleteById/{idProducto}", productoHandler::delete).build();
    }

}
