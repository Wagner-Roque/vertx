package com.wagner.vertx_starter.request;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class RequestVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(RequestVerticle.class);
  public static  String ADDRESS = "my.request.address";

  @Override
  public void start(final Promise<Void> starPromise) throws Exception {
    starPromise.complete();
    var eventBus = vertx.eventBus();
    String message = "Hello World";
    logger.debug("Sending : {}");
    eventBus.<String>request(ADDRESS, message, reply ->{
      logger.debug("Response: {}");
    });
  }
}
