package com.mycompany.messagesystem.service;

import com.mycompany.messagesystem.entity.Message;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public interface MessageService {

    public void deleteAllMessages(String userName);

    public boolean saveMessage(Message message);

    @Transactional(readOnly = true)
    public List<Message> getInboxMessages(String userName);

    @Transactional(readOnly = true)
    public List<Message> getSentMessages(String userName);

    public void deleteMessage(Long aLong, String messageType);
    
}
