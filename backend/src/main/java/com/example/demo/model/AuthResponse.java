package com.example.demo.model;


public class AuthResponse {
    private final long id;
    private final String name;
    private final String token;
    
    public AuthResponse(long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
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
}