package com.mycompany.messagesystem.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @NotNull(message = "Введите тему сообщения")
    private String title;
    private String fromUser;
    @Pattern(regexp = "^[a-zA-Z0-9-]+$",
            message = "Никнейм может содержать только буквы латинского алфавита")
    private String toUser;
    private String messageText;

    public Message() {
    }

    public Message(Long id, String title, String fromUser, String toUser, String messageText) {
        this.id = id;
        this.title = title;
        this.messageText = messageText;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format("Message from user %s to user %s with title %s",
                fromUser, toUser, title);
    }

    @Override
    public boolean equals(Object obj) {
        Message m;
        if (obj instanceof Message) {
            m = (Message) obj;
        } else {
            return false;
        }
        if (Objects.equals(m.getId(), id)
                && Objects.equals(m.getFromUser(), fromUser)
                && Objects.equals(m.getToUser(), toUser)
                && Objects.equals(m.getTitle(), title)
                && Objects.equals(m.getMessageText(), messageText)){
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
