package webapp.restfull.demo.accessingdatajpa.models;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode(of = {"id"})
public class Role {
    public Role(){}

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Role(Integer id) {
        this.id = id;
    }

    @Id
    @Column(unique = true, nullable = false)

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
