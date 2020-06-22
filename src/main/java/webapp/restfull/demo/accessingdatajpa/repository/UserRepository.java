package webapp.restfull.demo.accessingdatajpa.repository;
import org.springframework.data.repository.CrudRepository;
import webapp.restfull.demo.accessingdatajpa.models.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer>{
    List<User> findUsersByName(String name);
    Optional<User> findUserByLogin(String login);
    List<UserWithoutRoles> findAllProjectedBy();
    void deleteByLogin(String login);
    
    interface UserWithoutRoles {
        String getName();
        String getLogin();
        String getPassword();
        Integer getId();
    }
}
