package webapp.restfull.demo.accessingdatajpa.models;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    String login;

    String name;
    String password;

    @Transient
    Set<Integer> roles;

    @ManyToMany(fetch = FetchType.LAZY) //TODO: заменить на ManyToOne
    private Set<Role> rolesList;

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public User(){}

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public User(String login, String name, String password, Set<Role> rolesList) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.rolesList = rolesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Пользователь{" +
                "логин='" + login + '\'' +
                '}';
    }
}
