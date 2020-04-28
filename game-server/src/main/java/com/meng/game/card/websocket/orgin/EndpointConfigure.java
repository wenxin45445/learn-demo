package com.meng.game.card.websocket.orgin;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

public class EndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static volatile BeanFactory beanFactoryContext;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return beanFactoryContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EndpointConfigure.beanFactoryContext = applicationContext;
    }
}
