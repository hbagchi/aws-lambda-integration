package com.home.lambda.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.home.lambda.SOAPMessageWrapper;
import com.home.lambda.pojo.JsonResponseWrapper;

public class Utility {
	/**
	 * Converts a SOAP message to a String in UTF8 format
	 * 
	 * @param soapMessage
	 * @return
	 */
	public static String toXml(SOAPMessage soapMessage) {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			soapMessage.writeTo(byteArrayOutputStream);
			return byteArrayOutputStream.toString(UTF_8.name());
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert SOAPMessage to String", e);
		}
	}

	/**
	 * Retrieves the location name from the SOAP XML message
	 * 
	 * @return
	 */
	public static String getLocation(SOAPMessage soapMessage) {

		SOAPBody body;
		String location = "";

		try {
			body = soapMessage.getSOAPBody();
			NodeList nodes = body.getElementsByTagName("location");
			Node node = nodes.item(0);
			location = node != null ? node.getTextContent() : "";
		} catch (SOAPException e) {
			throw new RuntimeException("Failed to extract SOAPBody from SOAPMessage", e);
		}
		return location;
	}

	/**
	 * Converts the location field into a json string
	 * 
	 * @return
	 */
	public static String getJsonForInput(String input) {

		JsonObject queryParams = new JsonObject();

		JsonObject city = new JsonObject();
		city.addProperty("city", input);

		queryParams.add("queryStringParameters", city);

		String jsonStr = queryParams.toString();

		return jsonStr;
	}

	/**
	 * Invokes a Lambda function with the input json
	 * 
	 * @return
	 */
	public static String invokeLambdaFunction(String functionName, String inputJson) {

		Regions region = Regions.fromName("us-east-1");

		AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard().withRegion(region);
		AWSLambda client = builder.build();

		InvokeRequest request = new InvokeRequest().withFunctionName(functionName).withPayload(inputJson);

		InvokeResult result = client.invoke(request);
		ByteBuffer response = result.getPayload();

		return new String(response.array());
	}

	/**
	 * 
	 * @return
	 */
	public static SOAPMessageWrapper jsonToSoapWrapper(String json) throws SOAPException {

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPBody soapBody = message.getSOAPBody();

		Gson gson = new Gson();
		JsonResponseWrapper jsonWrapper = gson.fromJson(json, JsonResponseWrapper.class);

		String location = jsonWrapper.getBody().getCity();
		Double currLocationTemp = jsonWrapper.getBody().getCurrentTemperature();

		// both values are not null
		if (location != null && currLocationTemp != null) {

			SOAPElement soapBodyLocation = soapBody.addChildElement("location");
			soapBodyLocation.addTextNode(location);

			SOAPElement soapBodyCurrTemp = soapBody.addChildElement("CurrentTemperature");
			soapBodyCurrTemp.addTextNode(currLocationTemp.toString());

			message.saveChanges();
		} else {
			QName faultQname = new QName("Client");
			soapBody.addFault(faultQname, "No data for input value");
		}
		
		return new SOAPMessageWrapper(toXml(message));
	}
}
