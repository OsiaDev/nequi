package com.nequi.prueba.router;

import com.nequi.prueba.handler.SucursalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SucursalRouter {

    private static final String PATH = "sucursales";

    @Bean
    RouterFunction<ServerResponse> routerFunction(SucursalHandler sucursalHandler) {
        return RouterFunctions.route()
                .GET(PATH + "/getAll", sucursalHandler::getAll)
                .GET(PATH + "/getById/{idSucursal}", sucursalHandler::getById)
                .POST(PATH + "/create", sucursalHandler::save)
                .PUT(PATH + "/update/{idSucursal}", sucursalHandler::update)
                .DELETE(PATH + "/deleteById/{idSucursal}", sucursalHandler::delete).build();
    }

}
