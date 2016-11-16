package by.training.service.dao;

import by.training.entity.UserEntity;
import by.training.exception.ValidationException;

public interface UserServiceDAO {

    UserEntity createUser(UserEntity user) throws ValidationException;

    UserEntity getUserById(long id);

    UserEntity getUserByLogin(String login);

    boolean checkLogin(String login);

}
