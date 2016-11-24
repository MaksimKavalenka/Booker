package by.training.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.training.entity.UserEntity;
import by.training.service.dao.UserServiceDAO;

@Service("userDetailsSecurityService")
public class UserDetailsSecurityService implements UserDetailsService {

    private UserServiceDAO userService;

    public UserDetailsSecurityService(UserServiceDAO userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByLogin(username);
    }

}
