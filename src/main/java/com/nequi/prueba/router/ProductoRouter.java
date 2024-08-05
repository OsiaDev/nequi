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
                .GET(PATH + "/getMaxStock/{idFranquicia}", productoHandler::getMaxStockByFranquicia)
                .POST(PATH + "/create", productoHandler::save)
                .PUT(PATH + "/update/{idProducto}", productoHandler::update)
                .PATCH(PATH + "/updateName/{idProducto}", productoHandler::updateNombre)
                .DELETE(PATH + "/deleteById/{idProducto}", productoHandler::delete)
                .POST(PATH + "/addStock/{idProducto}", productoHandler::addStock)
                .POST(PATH + "/removeStock/{idProducto}", productoHandler::removeStock).build();
    }

}
