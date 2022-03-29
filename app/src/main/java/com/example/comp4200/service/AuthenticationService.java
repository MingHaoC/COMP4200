package com.example.comp4200.service;

public interface AuthenticationService {

    void register(String displayName, String handle, String email, String Password);

    void login(String email, String password);
}
