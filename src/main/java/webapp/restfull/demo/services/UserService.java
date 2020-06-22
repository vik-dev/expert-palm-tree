package webapp.restfull.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import webapp.restfull.demo.DemoApplication;
import webapp.restfull.demo.accessingdatajpa.models.Role;
import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.accessingdatajpa.repository.RoleRepository;
import webapp.restfull.demo.accessingdatajpa.repository.UserRepository;
import webapp.restfull.demo.models.UserError;
import webapp.restfull.demo.models.statuses.UserGetStatus;
import webapp.restfull.demo.models.statuses.UserUpdateStatus;

import java.util.*;

@Service
public class UserService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserUpdateStatus checkAndPrepareUser(User user){
        List<UserError> errors = new ArrayList<>();
        if (user.getLogin() == null || user.getLogin().equals("")) {
            errors.add(new UserError(UserError.Error.LOGIN_NOT_EXIST));
        }
        if (user.getName() == null || user.getName().equals("")) {
            errors.add(new UserError(UserError.Error.NAME_NOT_EXIST));
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            errors.add(new UserError(UserError.Error.PASSWORD_NOT_EXIST));
        } else {
            if (!user.getPassword().matches("^\\d+[A-ZА-Я]+|[A-ZА-Я]+\\d+$")) {
                if (!user.getPassword().matches("^.*\\d+.*$")) {
                    errors.add(new UserError(UserError.Error.DIGIT_NOT_EXIST));
                }
                if (!user.getPassword().matches("^.*[A-ZА-Я].*$")) {
                    errors.add(new UserError(UserError.Error.UPPER_CASE_NOT_EXIST));
                }
            }
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> roleList = new HashSet<>();
            for (Integer role : user.getRoles()) {
                roleRepository.findById(role).map(roleList::add).orElseGet(() ->
                        errors.add(new UserError(UserError.Error.UNKNOWN_ROLE, user))); //TODO, записывать в ошибку номер роли
            }
            user.setRolesList(roleList);
        }
        if (errors.isEmpty()) {
            return new UserUpdateStatus();
        } else {
            return new UserUpdateStatus(errors);
        }
    }

    public UserUpdateStatus delete(Integer id) {
        try {
            userRepository.deleteById(id);
            return new UserUpdateStatus(true);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            List<UserError> errors = new ArrayList<>();
            errors.add(new UserError(UserError.Error.UNKNOWN_ERROR));
            return new UserUpdateStatus(false, errors);
        }
    }

    public UserUpdateStatus save(User user) {
        UserUpdateStatus userUpdateStatus = checkAndPrepareUser(user);
        if (userUpdateStatus.getErrors() == null || userUpdateStatus.getErrors().isEmpty()){
            try {
                userUpdateStatus.setUser(userRepository.save(user));
                userUpdateStatus.setStatus(true);
                return userUpdateStatus;
            } catch (DataAccessException e){
                log.error(e.getMessage(), e);
                userUpdateStatus.addError(new UserError(UserError.Error.UNKNOWN_ERROR));
                userUpdateStatus.setStatus(false);
                return userUpdateStatus;
            }
        } else {
            userUpdateStatus.setStatus(false);
            return userUpdateStatus;
        }
    }

    public UserUpdateStatus edit(User user) {
        UserUpdateStatus userUpdateStatus = checkAndPrepareUser(user);
        if (userUpdateStatus.getErrors() == null || userUpdateStatus.getErrors().isEmpty()){
            try {
                User user1 = userRepository.findById(user.getId()).map(el -> {
                    user.setId(user.getId());
                    return userRepository.save(user);
                }).orElseGet(() -> {
                    userUpdateStatus.addError(new UserError(UserError.Error.TRY_TO_ADD_NEW_USER));
                    return null;
                });
                userUpdateStatus.setUser(user1);
                userUpdateStatus.setStatus(user1 != null);
                return userUpdateStatus;
            } catch (DataAccessException e){
                log.error(e.getMessage(), e);
                userUpdateStatus.addError(new UserError(UserError.Error.UNKNOWN_ERROR));
                userUpdateStatus.setStatus(false);
                return userUpdateStatus;
            }
        } else {
            userUpdateStatus.setStatus(false);
            return userUpdateStatus;
        }
    }

    public UserGetStatus getUser(String login) {
        log.debug("get User with login = {}", login);
        Optional<User> userOptional = userRepository.findUserByLogin(login);
        return userOptional.map(user -> new UserGetStatus(true, user)).orElseGet(() -> {
                    log.warn("user with login = {} not found", login);
                    List<UserError> errors = new ArrayList<>();
                    errors.add(new UserError(UserError.Error.USER_NOT_FOUND));
                    return new UserGetStatus(false, errors);
                }
        );
    }
}
