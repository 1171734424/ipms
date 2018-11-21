package com.ideassoft.bean.wrapper;

public class MultiQueryWorkbill implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1075100301628342130L;

	private String workbillid;
	private String name;
	private String status;
	private String currentdate;
	private String recorduser;
	private String finaluser;
	private String recorddatebegin;
	private String recorddateend;

	public String getWorkbillid() {
		return workbillid;
	}

	public void setWorkbillid(String workbillid) {
		this.workbillid = workbillid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}

	public String getRecorduser() {
		return recorduser;
	}

	public void setRecorduser(String recorduser) {
		this.recorduser = recorduser;
	}

	public String getFinaluser() {
		return finaluser;
	}

	public void setFinaluser(String finaluser) {
		this.finaluser = finaluser;
	}

	public String getRecorddatebegin() {
		return recorddatebegin;
	}

	public void setRecorddatebegin(String recorddatebegin) {
		this.recorddatebegin = recorddatebegin;
	}

	public String getRecorddateend() {
		return recorddateend;
	}

	public void setRecorddateend(String recorddateend) {
		this.recorddateend = recorddateend;
	}

}
