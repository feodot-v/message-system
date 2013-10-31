package com.mycompany.messagesystem.service;

import com.mycompany.messagesystem.entity.MUser;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public interface MUserService {
    
    @Transactional(readOnly = true)
    public List<String> getNamesList();
   
    @Transactional(readOnly = true)
    public MUser getUser(Long aLong);

    public boolean saveUser(MUser user);

    public boolean updateUser(MUser user);

    @Transactional(readOnly = true)
    public List<MUser> geAllUsers();

    public void deleteUser(Long aLong);
    
}
