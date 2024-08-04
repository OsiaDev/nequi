package com.nequi.prueba.router;

import com.nequi.prueba.handler.FranquiciaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranquiciaRouter {

    private static final String PATH = "franquicias";

    @Bean
    RouterFunction<ServerResponse> franquiciaRouterFunction(FranquiciaHandler franquiciaHandler) {
        return RouterFunctions.route()
                .GET(PATH + "/getAll", franquiciaHandler::getAll)
                .GET(PATH + "/getById/{idFranquicia}", franquiciaHandler::getById)
                .POST(PATH + "/create", franquiciaHandler::save)
                .PUT(PATH + "/update/{idFranquicia}", franquiciaHandler::update)
                .DELETE(PATH + "/deleteById/{idFranquicia}", franquiciaHandler::delete).build();
    }

}
