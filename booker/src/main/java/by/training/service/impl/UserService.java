package by.training.service.impl;

import static by.training.constants.MessageConstants.TAKEN_LOGIN_MESSAGE;

import org.springframework.beans.factory.annotation.Autowired;

import by.training.entity.UserEntity;
import by.training.exception.ValidationException;
import by.training.jpa.repository.UserRepository;
import by.training.service.dao.UserServiceDAO;

public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository repository;

    @Override
    public UserEntity createUser(UserEntity user) throws ValidationException {
        synchronized (UserService.class) {
            if (!checkLogin(user.getLogin())) {
                return repository.save(user);
            } else {
                throw new ValidationException(TAKEN_LOGIN_MESSAGE);
            }
        }
    }

    @Override
    public UserEntity getUserById(long id) {
        return repository.findOne(id);
    }

    @Override
    public UserEntity getUserByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public boolean checkLogin(String login) {
        return repository.checkLogin(login);
    }

}
