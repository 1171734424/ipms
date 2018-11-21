package com.ideassoft.core.annotation.processor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.bean.RightDefine;
import com.ideassoft.core.annotation.interfaces.RightMethodControl;
import com.ideassoft.core.annotation.interfaces.RightModelControl;
import com.ideassoft.core.constants.RightConstants;

@Component
@Lazy(true)
public class RightAnnotationPostProcessor implements BeanPostProcessor {
    private static final Logger logger = Logger.getLogger(RightAnnotationPostProcessor.class);

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		RightModelControl rightModeldControl = bean.getClass().getAnnotation(RightModelControl.class);
		
		if (rightModeldControl != null) {
	        String rightModel;
	        String[] urls;
	        RightDefine rightDefine;

            rightModel = rightModeldControl.rightModel();
            
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
            if (methods != null) {
                for (Method method : methods) {
                	RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        RightMethodControl rightMethodControl = method.getAnnotation(RightMethodControl.class);

                        if (rightMethodControl != null) {
                        	 urls = requestMapping.value();
                             rightDefine = new RightDefine();
                             rightDefine.setRightModel(rightModel);
                             rightDefine.setUrl(urls[0]);
                             rightDefine.setRightType(rightMethodControl.rightType());
                             
                             logger.info("加载权限设置: " + beanName + ",url=" + urls[0] + ":" + rightMethodControl.rightType());

                             RightConstants.rightDefineMap.put(urls[0], rightDefine);
						}
                    }
                }
            }
		}
		
        return bean;
	}
}
