package com.oyeoye.consumer.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.oyeoye.consumer.App;
import com.oyeoye.consumer.AppDependencies;
import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.R;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.User;
import com.oyeoye.consumer.rest.RestClient;

import javax.inject.Inject;

import autodagger.AutoComponent;
import autodagger.AutoInjector;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * @author jfcartier
 * @since 15-09-26
 */
@AutoComponent(
        dependencies = App.class,
        superinterfaces = AppDependencies.class
)
@DaggerScope(RegistrationIntentService.class)
@AutoInjector(RegistrationIntentService.class)
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    @Inject
    protected RestClient restClient;

    @Inject
    protected SessionManager sessionManager;

    public RegistrationIntentService() {
        super(TAG);
    }

    public RegistrationIntentService(String name) {
        super(name);

        App app = (App) getApplication();
        RegistrationIntentServiceComponent component = DaggerRegistrationIntentServiceComponent.builder()
                .appComponent(app.getComponent())
                .build();
        component.inject(this);
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

                // update gcm just in case if user is alreay logged
                if (sessionManager.isLogged()) {
                    restClient.getUserService().connect(sessionManager.getUser().getPhone(), token, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }
        } catch (Exception e) {
            Timber.e("Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SessionManager.GCM_TOKEN_SENT_TO_SERVER_KEY, false).apply();
        }
    }
}
