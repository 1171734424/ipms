package com.ideassoft.core.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.util.RequestUtil;

public class MySessionContext {
	private static MySessionContext instance;
	
	private Map<String, Object> mySession;

	private MySessionContext() {
		mySession = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	public static MySessionContext getInstance() {
		if (instance == null) {
			instance = new MySessionContext();
		}
		return instance;
	}

	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			mySession.put(session.getId(), session);
		}
	}

	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			mySession.remove(session.getId());
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mySession.get(session_id);
	}

	public synchronized HttpSession getSession(HttpSession session, String userId) {
		Entry<String, Object> entry;
		LoginUser loginUser;
		HttpSession sessionOtr, sessionSel = null;
		
		for (Iterator<Entry<String, Object>> it = mySession.entrySet().iterator(); it.hasNext();) {
			entry = it.next();
			sessionOtr = (HttpSession) entry.getValue();

			if (!session.getId().equalsIgnoreCase(sessionOtr.getId())) {
				loginUser = (LoginUser) sessionOtr.getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
				
				if(loginUser != null && loginUser.getStaff().getStaffId().equals(userId)) {
					sessionSel = sessionOtr;
					break;
				}
			}
		}
		
		return sessionSel;
	}
	
	public synchronized void delSession(HttpSession session, String userId) {
		if (!mySession.isEmpty()) {
			HttpSession sessionDel = getSession(session, userId);
			
			if (sessionDel != null) {
				delSession(sessionDel);
			}
		}
	}
	
}
