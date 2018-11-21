package com.ideassoft.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DialogBean {

	private String value;

	private String label;

	public DialogBean() {

	}

	public DialogBean(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@Id
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
