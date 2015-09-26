package com.oyeoye.consumer;

import com.oyeoye.consumer.manager.SessionManager;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public interface AppDependencies {

    SessionManager sessionManager();
}
