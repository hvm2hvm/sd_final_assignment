package models;

import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 28.05.2017
 */
@Entity
@Table(name = "role")
public class Role extends Model implements ModelWithID<Integer> {

    @Id
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "role_permissions")
    private List<Permission> permissions;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
