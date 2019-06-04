package com.zhiyun.readwrite.entity;

import com.zhiyun.readwrite.controller.WebSocket;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Title: User
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/6/49:40
 */
@Component
@Data
public class User {
    public String id;
    /**
     * 聊天室昵称
    * */
    public String nickname;
    public WebSocket webSocket;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User(String id, String nickname, WebSocket webSocket) {
        this.id = id;
        this.nickname = nickname;
        this.webSocket = webSocket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
