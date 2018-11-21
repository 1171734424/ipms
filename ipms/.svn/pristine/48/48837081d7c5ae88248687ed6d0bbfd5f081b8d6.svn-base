package com.ideassoft.core.annotation.processor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.core.annotation.interfaces.AlpMethodControl;
import com.ideassoft.core.annotation.interfaces.AlpModelControl;
import com.ideassoft.core.constants.AlpConstants;

@Component
@Lazy(true)
public class AlpAnnotationPostProcessor implements BeanPostProcessor {
    private static final Logger logger = Logger.getLogger(AlpAnnotationPostProcessor.class);

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		AlpModelControl AlpModeldControl = bean.getClass().getAnnotation(AlpModelControl.class);
		
		if (AlpModeldControl != null) {
	        String[] urls;
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            if (methods != null) {
                for (Method method : methods) {
                	RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        AlpMethodControl AlpMethodControl = method.getAnnotation(AlpMethodControl.class);

                        if (AlpMethodControl != null) {
                        	 urls = requestMapping.value();
                             
                             logger.info("加载权限设置: " + beanName + ",url=" + urls[0]);

                             AlpConstants.alpMap.put(urls[0], urls[0]);
						}
                    }
                }
            }
		}
		
        return bean;
	}
}
