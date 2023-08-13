//package com.ocean.server;
//
//
//import com.alibaba.fastjson2.JSON;
//import com.ocean.entity.Message;
//import jakarta.websocket.*;
//import jakarta.websocket.server.PathParam;
//import jakarta.websocket.server.ServerEndpoint;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ServerEndpoint(value = "/ws/{sid}")
//@Component
//public class WebsocketServer {
//    private final static Logger log = LoggerFactory.getLogger(WebsocketServer.class);
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。由于遍历set费时，改用map优化
//    //private static CopyOnWriteArraySet<WebsocketServer> webSocketSet = new CopyOnWriteArraySet<WebsocketServer>();
//    //新：使用map对象优化，便于根据sid来获取对应的WebSocket
//    private static ConcurrentHashMap<String, WebsocketServer> websocketMap = new ConcurrentHashMap<>();
//    //接收用户的sid，指定需要推送的用户
//    private String sid;
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    /**
//     * 连接成功后调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("sid") String sid) {
//        this.session = session;
//        websocketMap.put(sid, this); //加入map中
//        addOnlineCount();           //在线数加1
//        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
//        this.sid = sid;
//        try {
//            sendMessage("连接成功");
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        if (websocketMap.get(this.sid) != null) {
//            //webSocketSet.remove(this);  //从set中删除
//            websocketMap.remove(this.sid);  //从map中删除
//            subOnlineCount();           //在线数减1
//            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
//        }
//    }
//
//    /**
//     * 收到客户端消息后调用的方法，根据业务要求进行处理，这里就简单地将收到的消息直接群发推送出去
//     *
//     * @param message 客户端发送过来的消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自窗口" + sid + "的信息:" + message);
//        Message msg = JSON.parseObject(message, Message.class);
//        saveToRedis(msg, sid);
//        if (StringUtils.isNotBlank(message)) {
//            for (WebsocketServer server : websocketMap.values()) {
//                try {
//                    server.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void saveToRedis(Message message, String sid) {
//        // 将message中的destination与sid进行比较，拼接成字符串作为redis的key
//        // key的格式为：小号-大号
//        String key;
//        if (Integer.parseInt(sid) <= Integer.parseInt(message.getDestination())) {
//            key = sid + "-" + message.getDestination();
//        } else {
//            key = message.getDestination() + "-" + sid;
//        }
//        redisTemplate.opsForList().rightPush(key, message.getMessage());
//    }
//
//    /**
//     * 发生错误时的回调函数
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 实现服务器主动推送消息
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//    /**
//     * 群发自定义消息（用set会方便些）
//     */
//    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
//        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
//        /*for (WebsocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(sid==null) {
//                    item.sendMessage(message);
//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }*/
//        if (StringUtils.isNotBlank(message)) {
//            for (WebsocketServer server : websocketMap.values()) {
//                try {
//                    // sid为null时群发，不为null则只发一个
//                    if (sid == null) {
//                        server.sendMessage(message);
//                    } else if (server.sid.equals(sid)) {
//                        server.sendMessage(message);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//        }
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebsocketServer.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebsocketServer.onlineCount--;
//    }
//}
//
