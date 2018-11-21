package com.ideassoft.core.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ideassoft.core.constants.CommonParams;

public class LoginSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

	public static Map<String, Object> userMap = new HashMap<String, Object> ();
	
	private MySessionContext msc = MySessionContext.getInstance(); 

	public void sessionCreated(HttpSessionEvent event) {
		msc.addSession(event.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		msc.delSession(session);
		CommonParams.removeParam(session.getId());
	}

	public void attributeAdded(HttpSessionBindingEvent event) {

	}

	public void attributeRemoved(HttpSessionBindingEvent event) {

	}

	public void attributeReplaced(HttpSessionBindingEvent event) {

	}

}
