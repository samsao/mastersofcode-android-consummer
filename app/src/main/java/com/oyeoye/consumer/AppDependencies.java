package com.oyeoye.consumer;

import com.oyeoye.consumer.manager.SessionManager;
import com.oyeoye.consumer.rest.RestClient;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public interface AppDependencies {

    SessionManager sessionManager();

    RestClient restClient();
}
