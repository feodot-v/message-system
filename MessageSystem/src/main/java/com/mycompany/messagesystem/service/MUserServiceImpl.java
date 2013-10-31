package com.mycompany.messagesystem.service;

import com.mycompany.messagesystem.dao.UserDAO;
import com.mycompany.messagesystem.entity.MUser;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MUserServiceImpl implements MUserService{
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    @Qualifier("hibernate")
    private UserDAO userDAO;
    @Autowired
    private MessageService messageService;

    @Transactional(readOnly = true)
    @Override
    public MUser getUser(Long userID) {
        MUser user = userDAO.getUser(userID);
        logger.info("get "+user);
        return user;
    }
    
    @Override
    public boolean saveUser(MUser user) {
        if(!getNamesList().contains(user.getUserName())){
            logger.info("save "+user);
            userDAO.saveUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(MUser user) {
        MUser existUser = userDAO.getUser(user.getId());
        if(user.getId().equals(existUser.getId())&&user.getUserName().equals(existUser.getUserName())){
            userDAO.updateUser(user);
            logger.info("update "+user);
            return true;
        } else
            if(!getNamesList().contains(user.getUserName())){
            userDAO.updateUser(user);
            logger.info("update "+user);
            return true;
            }
        logger.info("updateUser return false");
        return false;
    }
    @Transactional(readOnly = true)
    @Override
    public List<MUser> geAllUsers() {
        List<MUser> users = userDAO.getAllUsers();
        if(users.isEmpty())logger.info("getUsersList() return an empty list");
        else logger.info("getUsersList() a list with users");
        return users;
    }
    @Transactional(readOnly = true)
    @Override
    public List<String> getNamesList() {
        return userDAO.getNamesList();
    }

    @Override
    public void deleteUser(Long userID) {
        MUser user = userDAO.getUser(userID);
        userDAO.deleteUser(user);
        messageService.deleteAllMessages(user.getUserName());
        logger.info("delete "+user);
    }

}
