package com.oyeoye.consumer.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.oyeoye.consumer.R;
import com.oyeoye.consumer.manager.SessionManager;

import timber.log.Timber;

/**
 * @author jfcartier
 * @since 15-09-26
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                sharedPreferences.edit().putString(SessionManager.GCM_TOKEN, token).apply();
                sharedPreferences.edit().putBoolean(SessionManager.GCM_TOKEN_SENT_TO_SERVER_KEY, true).apply();
            }
        } catch (Exception e) {
            Timber.e("Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SessionManager.GCM_TOKEN_SENT_TO_SERVER_KEY, false).apply();
        }
    }
}
