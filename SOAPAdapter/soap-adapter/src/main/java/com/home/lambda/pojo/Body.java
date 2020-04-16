package com.home.lambda.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("city")
	@Expose
	private String city;
	@SerializedName("currentTemperature")
	@Expose
	private Double currentTemperature;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(Double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

}
