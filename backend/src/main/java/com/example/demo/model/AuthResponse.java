package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

public class AuthResponse {
    private final long id;
    private final String name;
    private Set<Role> roles = new HashSet<>();
    private final String token;
    
    public AuthResponse(User user, String token) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.token = token;
        this.roles = user.getRoles();
    }


    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getToken() {
        return token;
    }

    public Set<Role> getRoles(){
        return roles;
    }
}