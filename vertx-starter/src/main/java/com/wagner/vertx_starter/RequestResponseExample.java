package com.wagner.vertx_starter;

import com.wagner.vertx_starter.request.RequestVerticle;
import com.wagner.vertx_starter.response.ResponseVerticle;
import io.vertx.core.Vertx;

public class RequestResponseExample {
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }
}
