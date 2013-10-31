package com.mycompany.messagesystem.dao.jpa;

import com.mycompany.messagesystem.dao.UserDAO;
import com.mycompany.messagesystem.entity.MUser;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

//@Repository
//@Qualifier("jpa")
public class JPAUserDAO implements UserDAO{
    @PersistenceContext
    private EntityManager em;
    private final String ALL_USERS = "select u from MUser u";
    private final String USERS_NAMES = "select u.userName from MUser u";

    @Override
    public void saveUser(MUser user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(MUser user) {
        em.remove(user);
    }

    @Override
    public void updateUser(MUser user) {
        em.merge(user);
    }

    @Override
    public MUser getUser(Long userID) {
        return em.find(MUser.class, userID);
    }
    
    

    @Override
    public List<MUser> getAllUsers() {
        Query q = em.createQuery(ALL_USERS);
        return (List<MUser>)q.getResultList();
    }

    @Override
    public List<String> getNamesList() {
        Query q = em.createQuery(USERS_NAMES);
        return (List<String>)q.getResultList();
    }
    
    
}
