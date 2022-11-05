package com.bahar.gamescore;

import com.bahar.gamescore.api.UrlLoginComponents;
import com.bahar.gamescore.api.UrlScoreComponent;
import com.bahar.gamescore.exception.ApplicationExceptions;
import com.bahar.gamescore.handler.LoginHandler;
import com.bahar.gamescore.handler.ScoreHandler;
import com.bahar.gamescore.util.ApiUtils;
import com.bahar.gamescore.util.UrlComponents;
import com.bahar.gamescore.handler.HighScoreHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.bahar.gamescore.Configuration.*;

public class Application {
    public static void main(String[] args) throws IOException {
        int serverPort = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        server.createContext("/",
                (exchange -> {
                    UrlComponents urlComponents = ApiUtils.splitUrl(exchange.getRequestURI().getRawPath())
                            .orElseThrow(ApplicationExceptions.invalidRequest("Invalid URL"));


                    switch (urlComponents.getServiceName()) {
                        case LOGIN:
                            new LoginHandler(getLoginService(),
                                    getObjectMapper(),
                                    getErrorHandler(),
                                    new UrlLoginComponents(urlComponents.getIdentifier(), urlComponents.getServiceName())
                            ).handle(exchange);
                            break;
                        case SCORE:

                            new ScoreHandler(getScoreService(),
                                    getObjectMapper(),
                                    getErrorHandler(),
                                    new UrlScoreComponent(null, urlComponents.getServiceName(), urlComponents.getIdentifier()),
                                    getLoginRepository()).handle(exchange);
                            break;

                        case HIGH_SCORE_LIST:
                            new HighScoreHandler(getScoreService(),
                                    getObjectMapper(),
                                    getErrorHandler(),
                                    new UrlScoreComponent(null, urlComponents.getServiceName(), urlComponents.getIdentifier())
                                    ).handle(exchange);
                            break;
                    }

                   
//                    if ("GET".equals(exchange.getRequestMethod())) {
//                        Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
//                        String noNameText = "Anonymous";
//                        String name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
//                        String respText = String.format("Hello %s!", name);
//                        exchange.sendResponseHeaders(200, respText.getBytes().length);
//                        OutputStream output = exchange.getResponseBody();
//                        output.write(respText.getBytes());
//                        output.flush();
//                    } else {
//                        exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
//                    }
                    exchange.close();
                })

        );

        server.setExecutor(null);
        server.start();

    }

}
