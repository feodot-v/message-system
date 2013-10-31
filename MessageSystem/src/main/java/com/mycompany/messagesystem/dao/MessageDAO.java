package com.mycompany.messagesystem.dao;

import com.mycompany.messagesystem.entity.Message;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface MessageDAO {
    
    void saveMessage(Message message);
    
    void deleteMessage(Message message);
    
    List<Message> getInboxtMessages(String userName);
    
    List<Message> getSenttMessages(String userName);
    
    Message getMessage(Long messageID);
}
