package com.example.comp4200.service;

import android.content.Context;

public interface AuthenticationService {

    void register(Context context, String displayName, String handle, String email, String password, String description);

    void login(Context context, String email, String password);

    void logout(Context context);
}
