package com.mycompany.messagesystem.dao;

import com.mycompany.messagesystem.entity.MUser;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
@Repository
public interface UserDAO {
    @CacheEvict(value = "lists", allEntries = true)
    void saveUser(MUser user);
    @CacheEvict(value = "lists", allEntries = true)
    void deleteUser(MUser user);
    @CacheEvict(value = "lists", allEntries = true)
    void updateUser(MUser user);
    @Cacheable(value = "lists", key = "#root.methodName")
    List<MUser> getAllUsers();
    @Cacheable(value = "lists", key = "#root.methodName")
    List<String> getNamesList();

    public MUser getUser(Long userID);
}
