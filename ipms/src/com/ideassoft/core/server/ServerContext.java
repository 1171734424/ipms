package com.ideassoft.core.server;

import java.util.Hashtable;

public final class ServerContext {

	private final Hashtable<String, Object> context = new Hashtable<String, Object>();
	
	ServerContext() {

	}

	public synchronized void setAttribute(String key, Object value) {
		if (context.containsKey(key) == false) {
			context.put(key, value);
		}
	}

	public synchronized Object getAttribute(String key) {
		return context.get(key);
	}

	public Object removeAttritue(String key) {
		return context.remove(key);
	}

	public void removeAllAttribute() {
		context.clear();
	}
}
