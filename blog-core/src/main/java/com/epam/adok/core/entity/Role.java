package com.epam.adok.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLES")
public class Role extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
