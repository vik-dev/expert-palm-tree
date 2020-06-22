package webapp.restfull.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import webapp.restfull.demo.DemoApplication;
import webapp.restfull.demo.accessingdatajpa.models.Role;
import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.accessingdatajpa.repository.RoleRepository;
import webapp.restfull.demo.accessingdatajpa.repository.UserRepository;
import webapp.restfull.demo.models.statuses.UserGetStatus;
import webapp.restfull.demo.models.statuses.UserUpdateStatus;
import webapp.restfull.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserApiController {

    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);


    public UserApiController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("list")
    public List<UserRepository.UserWithoutRoles> list() {
        log.debug("get all users");
        return userRepository.findAllProjectedBy();
    }

    @GetMapping("role_list")
    public List<Role> getRoles() {
        log.debug("get all roles");
        return roleRepository.findAll();
    }

    @GetMapping("get/{id}")
    public UserGetStatus getUser(@PathVariable String id) {
        return userService.getUser(id);
    }


    @DeleteMapping("delete{id}")
    public UserUpdateStatus delete(@PathVariable Integer id) {
        return userService.delete(id);
    }

    @PostMapping("save")
    public UserUpdateStatus save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("edit")
    public UserUpdateStatus edit(@RequestBody User user) {
        return userService.edit(user);
    }
}
