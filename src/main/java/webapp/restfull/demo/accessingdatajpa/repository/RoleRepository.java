package webapp.restfull.demo.accessingdatajpa.repository;

import org.springframework.data.repository.CrudRepository;
import webapp.restfull.demo.accessingdatajpa.models.Role;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();
}
