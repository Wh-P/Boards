package com.example.community_board.a01_config;

import com.example.community_board.a02_Controller.A01_ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  private final A01_ChatHandler chatHandler;
  public WebSocketConfig(A01_ChatHandler chatHandler) {
    this.chatHandler = chatHandler;
  }
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // backend(A10_ChattingHandler) 와 /chat 으로 front 에서 호출 시, 연동된다.
    registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");

  }



}

