package com.oyeoye.consumer.manager;

import android.content.SharedPreferences;

import com.oyeoye.consumer.model.User;

import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class SessionManager {

    public final static String GCM_TOKEN_SENT_TO_SERVER_KEY = "com.oyeoye.merchant.business.GCM_TOKEN_SENT_TO_SERVER_KEY";
    public final static String GCM_TOKEN = "com.oyeoye.merchant.business.GCM_TOKEN";
    private final SharedPreferences sharedPreferences;
    private User user;

    public SessionManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        load();
    }

    private void load() {
        String phone = sharedPreferences.getString("phone", null);
        if (phone == null) {
            return;
        }

        user = new User();
        user.setPhone(phone);
    }

    private void save() {
        sharedPreferences.edit()
                .putString("phone", user.getPhone())
                .apply();
    }

    public boolean isLogged() {
        return user != null;
    }

    public void authenticate(String phone) {
        user = new User();
        user.setPhone(phone);
        save();
    }

    public String getGcmToken() {
        return sharedPreferences.getString(GCM_TOKEN, null);
    }

    public User getUser() {
        return user;
    }
}
