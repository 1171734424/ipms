package com.ideassoft.test;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Cacheable(value = "realtimeCache", key = "#abcd")
	public long abc(String abcd, String dd) {
		return System.currentTimeMillis();
	}

}
