package com.example.demo.model;

import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "User")
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "username_unique", columnNames = "username") })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 21, nullable = false)
    @Size(min = 8, max = 21)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles = new HashSet<>();
    
    public User() {
    }
    
    public User(String username, @Size(min = 8, max = 21) String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Long id, String username, @Size(min = 8, max = 21) String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}