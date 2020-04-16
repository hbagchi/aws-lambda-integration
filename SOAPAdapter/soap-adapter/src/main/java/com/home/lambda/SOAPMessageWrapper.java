package com.home.lambda;

import java.util.Objects;

/**
 * 
 * A POJO to wrap a SOAP XML Message 
 *
 */
public class SOAPMessageWrapper {

	private String body;

	public SOAPMessageWrapper() {

	}

	public SOAPMessageWrapper(String body) {
		this.body = body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SOAPMessageWrapper))
			return false;
		SOAPMessageWrapper wrapper = (SOAPMessageWrapper) o;
		return Objects.equals(body, wrapper.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body);
	}

	@Override
	public String toString() {
		return "SoapWrapper{" + "body='" + body + '\'' + '}';
	}
}
