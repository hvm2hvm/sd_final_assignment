package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 28.05.2017
 */
@Entity
@Table(name = "account")
public class Account extends Model implements ModelWithID<Integer> {

    @Id
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String username;

    @Column(columnDefinition = "TEXT")
    private String fullname;

    @Column(columnDefinition = "TEXT")
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String token;

    @ManyToMany
    @JoinTable(
        name = "account_roles")
    private List<Role> roles;

    @Transient
    @JsonIgnore
    private String password;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Transient
    @JsonIgnore
    public boolean hasPermission(String neededPermission) {
        for (Role role : getRoles()) {
            for (Permission permission : role.getPermissions()) {
                if (permission.getName().equals(neededPermission)) {
                    return true;
                }
            }
        }
        return false;
    }
}
