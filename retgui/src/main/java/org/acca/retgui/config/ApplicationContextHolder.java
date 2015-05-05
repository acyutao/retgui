/*
 * Copyright (c) Travelsky Corp.
 * All Rights Reserved.
 */
package org.acca.retgui.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext Holder.
 *
 * @version Araf v1.0
 * @author Yu Tao, 2014-4-24
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;

    /**
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        
        this.applicationContext = applicationContext;

    }
    
    /**
     * Get Spring ApplicationContext.
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

}
