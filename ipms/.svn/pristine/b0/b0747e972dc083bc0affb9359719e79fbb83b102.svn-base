package com.ideassoft.core.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.util.RequestUtil;


public class HibernateSessionPerRequestHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(HibernateSessionPerRequestHandlerInterceptor.class);

    private SessionFactory sessionFactory;
    private List<String> ignoreUrls;

    /**
     * 开启Hibernate Session和事务
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        String url = request.getRequestURI();
        if (!urlIgnore(url)) {
            logger.debug("开启Hibernate session 并开始 一个事务.");

            Session sess = sessionFactory.openSession();
            sess.beginTransaction();
            
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(sess));
            TransactionSynchronizationManager.initSynchronization();
        }
        return true;
    }

    /**
     * 提交数据库事务并关闭Hibernate Session
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

        String url = request.getRequestURI();
        if (!urlIgnore(url)) {
            Session sess = sessionFactory.getCurrentSession();

            try {
                if (modelAndView != null) {
                    String result = (String) modelAndView.getModelMap().get("result");
                    if (!"-1".equals(result)) {
                        logger.debug("提交数据库事务.");
                        sess.getTransaction().commit();
                        
                        modelAndView.addObject("result", "1");
                    } else {
                        logger.debug("业务方法执行失败(result = -1), 回滚数据库事务.");
                        sess.getTransaction().rollback();
                    }
                } else {
                    sess.getTransaction().commit();
                }
            } catch (Exception e) {

                logger.error("发生异常! 回滚数据库事务! " + e.getMessage(), e);
                logger.error(RequestUtil.toStr(request));
                sess.getTransaction().rollback();

                if (modelAndView != null) {
                    modelAndView.addObject("message", "数据库操作异常! 请联系管理员.");
                    modelAndView.addObject("result", "-1");

                    modelAndView.addObject("exception", e);
                    modelAndView.setViewName("error");

                    logger.error("转到错误信息页面.");
                }
                
            } finally {
                try {
                    if (sess.isOpen()) {
                        logger.debug("关闭Hibernate Session.");
                        sess.close();
                    }
                } catch (Exception e) {
                    logger.warn("关闭Hibernate Session失败! " + e.getMessage(), e);
                }
                TransactionSynchronizationManager.unbindResource(sessionFactory);
                TransactionSynchronizationManager.clearSynchronization();
            }
        }
    }

    /**
     * 业务方法发生异常, 回滚事务, 关闭Session.
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        String url = request.getRequestURI();
        if (!urlIgnore(url)) {
            if (ex != null) {
                if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
                    Session sess = sessionFactory.getCurrentSession();

                    logger.error("发生异常! 回滚数据库事务! " + ex.getMessage(), ex);
                    sess.getTransaction().rollback();

                    try {
                        if (sess.isOpen()) {
                            logger.debug("关闭Hibernate Session.");
                            sess.close();
                        }
                    } catch (Exception e) {
                        logger.warn("关闭Hibernate Session失败! " + e.getMessage(), e);
                    }
                    TransactionSynchronizationManager.unbindResource(sessionFactory);
                    TransactionSynchronizationManager.clearSynchronization();
                }
            }
        }
    }

    /**
     * 设置SessionFactory,由配置文件设置
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    private boolean urlIgnore(String url) {
        boolean ignore = false;
        for (int i = 0; i < ignoreUrls.size(); i++) {
            if (url.indexOf(ignoreUrls.get(i)) != -1) {
                ignore = true;
                break;
            }
        }

        return ignore;
    }
}
