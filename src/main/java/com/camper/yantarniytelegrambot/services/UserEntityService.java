package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Schedule;
import com.camper.yantarniytelegrambot.entity.UserEntity;
import com.camper.yantarniytelegrambot.enums.UserRole;
import com.camper.yantarniytelegrambot.repos.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserEntityService {
    private UserEntityRepo userEntityRepo;

    /**
     * retrieve an entity from the database if it exists or else put user in database
     * @param user an example entity to retrieve from the database or a candidate to add to the database
     * @return user from database
     */
    public UserEntity putIfAbsent(UserEntity user) {
        UserEntity userFromDB = findUserByChatId(user.getChatId());
        if (userFromDB != null) {
            return userFromDB;
        } else {
            return saveUser(user);
        }
    }

    public UserEntity saveUser(UserEntity user) {
        return userEntityRepo.save(user);
    }

    @Transactional(readOnly = true)
    public UserEntity findUserByChatId(String chatId) {
        return userEntityRepo.findByChatId(chatId);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userEntityRepo.findAll();
    }

    @Autowired
    public void setUserEntityRepo(UserEntityRepo userEntityRepo) {
        this.userEntityRepo = userEntityRepo;
    }
}
