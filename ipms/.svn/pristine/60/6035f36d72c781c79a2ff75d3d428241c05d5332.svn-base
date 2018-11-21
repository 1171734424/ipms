package com.ideassoft.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
	
    private ApplicationContext applicationContext;
    
    private static ApplicationContext staticapplicationcontext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        SpringUtil.staticapplicationcontext = applicationContext;
    }

    public static Object getBean(String name) {
        if (SpringUtil.staticapplicationcontext != null) {
            return SpringUtil.staticapplicationcontext.getBean(name);
        }
        
        return null;
    }
    
    public static ApplicationContext getApplicationContext() {
    	return staticapplicationcontext;
    }
    
    public ApplicationContext getContext() {
    	return applicationContext;
    }
}
