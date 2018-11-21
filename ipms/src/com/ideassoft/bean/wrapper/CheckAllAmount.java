package com.ideassoft.bean.wrapper;

import java.io.Serializable;

public class CheckAllAmount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2042051033382007581L;

	private double pay;
	private double cost;
	private double checkoutPay;
	private double checkoutCost;
	private double uncheckoutPay;
	private double uncheckoutCost;
	private double chosedPay;
	private double chosedCost;
	private double subPrice;
	private double allPay;
	private double shouldPay;
	private double roomPrice;
	private double ttv;
	private String subpriceType;
	private String checkStatus;
	
	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getSubPrice() {
		return subPrice;
	}

	public void setSubPrice(double subPrice) {
		this.subPrice = subPrice;
	}

	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public double getTtv() {
		return ttv;
	}

	public void setTtv(double ttv) {
		this.ttv = ttv;
	}

	public void setSubpriceType(String subpriceType) {
		this.subpriceType = subpriceType;
	}

	public String getSubpriceType() {
		return subpriceType;
	}

	public void setCheckoutPay(double checkoutPay) {
		this.checkoutPay = checkoutPay;
	}

	public double getCheckoutPay() {
		return checkoutPay;
	}

	public void setCheckoutCost(double checkoutCost) {
		this.checkoutCost = checkoutCost;
	}

	public double getCheckoutCost() {
		return checkoutCost;
	}

	public void setUncheckoutPay(double uncheckoutPay) {
		this.uncheckoutPay = uncheckoutPay;
	}

	public double getUncheckoutPay() {
		return uncheckoutPay;
	}

	public void setUncheckoutCost(double uncheckoutCost) {
		this.uncheckoutCost = uncheckoutCost;
	}

	public double getUncheckoutCost() {
		return uncheckoutCost;
	}

	public void setAllPay(double allPay) {
		this.allPay = allPay;
	}

	public double getAllPay() {
		return allPay;
	}

	public void setShouldPay(double shouldPay) {
		this.shouldPay = shouldPay;
	}

	public double getShouldPay() {
		return shouldPay;
	}

	public void setChosedPay(double chosedPay) {
		this.chosedPay = chosedPay;
	}

	public double getChosedPay() {
		return chosedPay;
	}

	public void setChosedCost(double chosedCost) {
		this.chosedCost = chosedCost;
	}

	public double getChosedCost() {
		return chosedCost;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckStatus() {
		return checkStatus;
	}


}
