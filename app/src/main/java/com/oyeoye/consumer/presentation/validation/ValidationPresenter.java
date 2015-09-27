package com.oyeoye.consumer.presentation.validation;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;

import com.oyeoye.consumer.DaggerScope;
import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.model.Transaction;
import com.oyeoye.consumer.presentation.AbstractPresenter;
import com.oyeoye.consumer.presentation.ActivityContainerComponent;
import com.oyeoye.consumer.presentation.RootActivityPresenter;
import com.oyeoye.consumer.rest.RestClient;

import architect.robot.AutoStackable;
import architect.robot.FromPath;
import autodagger.AutoComponent;
import autodagger.AutoExpose;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
@AutoStackable(
        component = @AutoComponent(includes = ActivityContainerComponent.class),
        pathWithView = ValidationView.class
)
@DaggerScope(ValidationPresenter.class)
@AutoExpose(ValidationPresenter.class)
public class ValidationPresenter extends AbstractPresenter<ValidationView> {

    private final Transaction transaction;

    private final RootActivityPresenter activityPresenter;
    private final SessionManager sessionManager;
    private final RestClient restClient;

    public ValidationPresenter(@FromPath Transaction transaction, RootActivityPresenter activityPresenter, SessionManager sessionManager, RestClient restClient) {
        this.transaction = transaction;
        this.activityPresenter = activityPresenter;
        this.sessionManager = sessionManager;
        this.restClient = restClient;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getView().getContext());
        if (nfcAdapter != null) {
            nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
                @Override
                public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
                    return new NdefMessage(NdefRecord.createTextRecord("en", "test"));
                }
            }, activityPresenter.getActivity());
        }
    }

}
