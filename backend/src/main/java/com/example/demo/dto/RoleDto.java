package com.example.demo.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleDto implements Serializable{
    private long id;
    private String name;
    private Set<UserDto> users = new HashSet<>();
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<UserDto> getUsers() {
        return users;
    }
    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }
    public RoleDto(String name){
        this.name = name;
    }
}
