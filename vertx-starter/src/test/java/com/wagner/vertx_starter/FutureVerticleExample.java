package com.wagner.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FutureVerticleExample {
  private static final Logger Log =  LoggerFactory.getLogger(FutureVerticleExample.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext testContext){
    final Promise<String> promise = Promise.promise();
    Log.debug("Start");
    vertx.setTimer(500, id ->{
      promise.complete("Success");
      Log.debug("Success");
      testContext.completeNow();
    });
    Log.debug("End");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext testContext){
    final Promise<String> promise = Promise.promise();
    Log.debug("Start");
    vertx.setTimer(500, id ->{
      promise.fail(new RuntimeException("Failed"));
      Log.debug("Failed");
      testContext.completeNow();
    });
    Log.debug("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext testContext){
    final Promise<String> promise = Promise.promise();
    Log.debug("Start");
    vertx.setTimer(500, id ->{
      promise.complete("Success");
      Log.debug("Timer done.");
      testContext.completeNow();
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result ->{
    Log.debug("Result: {}");
       testContext.completeNow();
    })
      .onFailure(testContext::failNow);
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext testContext){
    final Promise<String> promise = Promise.promise();
    Log.debug("Start");
    vertx.setTimer(500, id ->{
      promise.fail(new RuntimeException("Failed"));
      Log.debug("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result ->{
        Log.debug("Result: {}");
        testContext.completeNow();
      })
      .onFailure(error ->{
        Log.debug("Result: ", error);
        testContext.completeNow();
      });
  }
}
