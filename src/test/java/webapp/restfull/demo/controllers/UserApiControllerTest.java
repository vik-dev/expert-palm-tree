package webapp.restfull.demo.controllers;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import webapp.restfull.demo.accessingdatajpa.models.Role;
import webapp.restfull.demo.accessingdatajpa.models.User;
import webapp.restfull.demo.accessingdatajpa.repository.UserRepository;
import webapp.restfull.demo.models.statuses.UserUpdateStatus;
import webapp.restfull.demo.services.UserService;
import webapp.restfull.demo.utils.SetUtils;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//TODO: в userRepository не работают методы
@RunWith(SpringRunner.class)
@SpringBootTest
class UserApiControllerTest  {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    User testUser1;
    User testUser2;

    @Before
    public void setUp() {

        User user1 = new User("testqweqwe", "tesqweqweter", "testoqweviCh1");
        when(userRepository.save(user1)).thenReturn(testUser1);

        User user2 = new User("testqweqwe", "tesqweqweter", "testoqweviCh1");
        when(userRepository.save(user2)).thenReturn(testUser2);

        assertNotNull(testUser1);
        assertNotNull(testUser2);
    }
    @Test
    void delete() {
        userService.delete(testUser1.getId());

        assertFalse(userRepository.findById(testUser1.getId()).isPresent());
    }

    @Test
    void save() {
        UserUpdateStatus test1 = userService.save(new User("testqweqwe", "tesqweqweter", "testoqwevih1"));
        assertFalse(test1.getStatus());
        UserUpdateStatus test2 = userService.save(new User("test1qweqwe", "tesqweqweter", "testoqwevIh"));
        assertFalse(test2.getStatus());
        UserUpdateStatus test3 = userService.save(new User("testqweq1we", "tesqweqweter", "testoqwevI1h"));
        assertTrue(test3.getStatus());
        userService.delete(test3.getUser().getId());
    }

    @Test
    void edit() {
        Set<Role> set = new HashSet<>();
        set.add(new Role(1));
        set.add(new Role(2));
        set.add(new Role(3));
        testUser2.setRolesList(set);
        UserUpdateStatus edit = userService.edit(testUser2);
        User user = edit.getUser();
        assertTrue(SetUtils.equals(set, user.getRolesList()));
        assertEquals(user.getRolesList(), set);
    }

}