package com.wagner.vertx_starter.response;

import com.wagner.vertx_starter.request.RequestVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class ResponseVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(ResponseVerticle.class);
  private static final String ADDRESS = "my.request.address";

  @Override
  public void start(final Promise<Void> starPromise) throws Exception {
    starPromise.complete();
    vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS, message -> {
      logger.debug("Received Message: {}");
      message.reply("Received your message. Thanks!");
    });

  }
}
