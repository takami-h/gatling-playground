package com.natswell.example.gatling;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class HomeVisitor extends Simulation {
  HttpProtocolBuilder httpProtocol = http
      .warmUp("http://localhost:8080")
      .baseUrl("http://localhost:8080");

  ScenarioBuilder scenario = scenario("someone visits home and see a file 10 times")
      .repeat(10).on(
          exec(http("visits home").get("/"))
          .pause(1)
          .exec(http("sees a file").get("/pom.xml"))
      );

  {
    setUp(scenario.injectOpen(rampUsers(100).during(5)))
      .protocols(httpProtocol);
  }
}
