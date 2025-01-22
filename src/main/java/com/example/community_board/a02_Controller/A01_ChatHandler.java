package com.example.community_board.a02_Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/* 컨테이너에서 chatHandler 라는 이름으로 등록하는데 이는 front 단에서 chat-ws로 호출가능하다.
 */
// ws:localhost:3030/chat   js 로 호출 시, chatHandler backend 연동 WebSocketConfig 에서 처리 해준다.
@Component("chatHandler")
public class A01_ChatHandler extends TextWebSocketHandler{
  //w접속한 클라이언트 할당..(소켓아이디, 소켓세션으로)
  private Map<String, WebSocketSession> users = new ConcurrentHashMap();


  // 접속 시 처리되는 메서드
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // TODO Auto-generated method stub
    //super.afterConnectionEstablished(session);
    System.out.println("[소켓 서버단]접속되었습니다"+session.getId());
    //접속 했을 때, 하나씩 할당 처리..
    users.put(session.getId(), session);
  }
  // 메세지를 전송 시 처리
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    // 클라이언트로부터 전달할 메시지
    System.out.println(session.getId()+":"+message.getPayload());

    // 접속한 모든 클라이언트에 전달(메세지 전달.)
    for(WebSocketSession ws: users.values()) {
      ws.sendMessage(message);
    }
  }
  // 접속을 종료 했을 떄..
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    users.remove(session.getId());
    System.out.println("#접속 종료! 해당 session id로 제외 처리#");
  }
  // 채팅시 에러 발생 시..
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    // TODO Auto-generated method stub
    System.out.println(session.getId()+"예외 발생:"+exception.getMessage());
  }

}
