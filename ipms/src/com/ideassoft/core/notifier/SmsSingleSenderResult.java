package com.ideassoft.core.notifier;

public class SmsSingleSenderResult {
	public int result;
	public String errMsg = "";
	public String ext = "";
	public String sid = "";
	public int fee;
	
	public String toString() {
		return String.format(
				"SmsSingleSenderResult\nresult %d\nerrMsg %s\next %s\nsid %s\nfee %d",
				result, errMsg, ext, sid, fee);		
	}
}
