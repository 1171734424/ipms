package com.ideassoft.bean.wrapper;

import java.io.Serializable;

public class MultiQueryHalt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150381280750931512L;

	private String logid;
	private String roomid;
	private String halttype;
	private String haltreason;
	private String starttime;
	private String endtime;
	private String status;
	private String type;

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getHalttype() {
		return halttype;
	}

	public void setHalttype(String halttype) {
		this.halttype = halttype;
	}

	public String getHaltreason() {
		return haltreason;
	}

	public void setHaltreason(String haltreason) {
		this.haltreason = haltreason;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
