package com.github.fwfurtado.tinyurl.transport.http;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ShortenSimulation extends Simulation {

    FeederBuilder<String> urls = csv("top-1m.csv").random();

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080/")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");


    ScenarioBuilder scn = scenario("ShortenSimulation")
            .feed(urls)
            .exec(http("shorten")
                    .post("/shorten")
                    .body(StringBody("{\"url\":\"https://#{site}/\"}"))
                    .asJson()
                    .check(jsonPath("$.url").exists())
                    .check(jsonPath("$.tinyUrl").exists())
            );

    {

        setUp(
                scn.injectOpen(
                        rampUsersPerSec(10).to(100).during(10),
                        stressPeakUsers(1000).during(20)
                )
        ).protocols(httpProtocol);
    }
}
