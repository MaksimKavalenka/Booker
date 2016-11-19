package by.training.controller.rest;

import static by.training.constants.URLConstants.Rest.USERS_URL;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.common.Role;
import by.training.dto.RegisterDTO;
import by.training.entity.UserEntity;
import by.training.exception.ValidationException;
import by.training.service.dao.RoleServiceDAO;
import by.training.service.dao.UserServiceDAO;
import by.training.utility.Parser;
import by.training.utility.Secure;

@RestController
@RequestMapping(USERS_URL)
public class UserRestController {

    @Autowired
    private RoleServiceDAO roleService;
    @Autowired
    private UserServiceDAO userService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> authentication(Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserEntity user = (UserEntity) ((AbstractAuthenticationToken) principal)
                        .getPrincipal();
                return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserEntity>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody @Valid RegisterDTO registerDto,
            Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<Object>(new ErrorMessage(Parser.getErrorsMessages(errors)),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            Set<GrantedAuthority> roles = new HashSet<>(1);
            roles.add(roleService.getRoleByName(Role.ROLE_USER.name()));

            UserEntity user = new UserEntity(registerDto.getLogin(),
                    Secure.secureBySha(registerDto.getPassword(), registerDto.getLogin()), roles);
            userService.createUser(user);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/check/login/{login}", method = RequestMethod.POST)
    public ResponseEntity<Object> checkLogin(@PathVariable("login") String login) {
        boolean exists = userService.checkLogin(login);
        return new ResponseEntity<Object>(exists, HttpStatus.OK);
    }

}
