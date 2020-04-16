package com.home.lambda.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonResponseWrapper {

	@SerializedName("statusCode")
	@Expose
	private Integer statusCode;
	@SerializedName("body")
	@Expose
	private Body body;

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

}
