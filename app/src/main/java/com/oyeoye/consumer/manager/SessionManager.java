package com.oyeoye.consumer.manager;

import android.content.SharedPreferences;

import com.oyeoye.consumer.model.User;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class SessionManager {

    private final SharedPreferences sharedPreferences;
    private User user;

    public SessionManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        load();
    }

    private void load() {
        if (sharedPreferences.getString("phone", null) == null) {
            return;
        }

        user = new User();
        user.setPhone(sharedPreferences.getString("phone", null));
    }

    private void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", user.getPhone());
        editor.apply();
    }

    public boolean isLogged() {
        return user != null;
    }

    public void authenticate(String phone) {
        user = new User();
        user.setPhone(phone);
        save();
    }
}
