package com.wagner.vertx_starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FutureMapVertx {
  private static final Logger Log =  LoggerFactory.getLogger(FutureMapVertx.class);
  @Test
  void future_map(Vertx vertx, VertxTestContext testContext){
    final Promise<String> promise = Promise.promise();
    Log.debug("Start");
    vertx.setTimer(500, id ->{
      promise.complete("Success");
      Log.debug("Timer done.");
      testContext.completeNow();
    });
    final Future<String> future = promise.future();
    future
      .map(asString ->{
        Log.debug("Map String to JsonObject");
        return new JsonObject().put("key", asString);
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result ->{
        Log.debug("Result: {} of type {}");
        testContext.completeNow();
      })
      .onFailure(testContext::failNow);
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext vertxTestContext){
    vertx.createHttpServer()
      .requestHandler(request -> Log.debug("{}"))
      .listen(10_000)
      .compose(server ->{
        Log.info("Another task");
        return  Future.succeededFuture(server);
      })
      .compose(server ->{
        Log.info("Even more");
        return Future.succeededFuture(server);
      })
      .onFailure(vertxTestContext::failNow)
      .onSuccess(server ->{
        Log.debug("Server started on port {}");
        vertxTestContext.completeNow();
      });
  }
}
