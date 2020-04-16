package com.home.lambda;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.home.lambda.util.Utility;

/**
 * Entry point for AWS Lambda function.
 * 
 * This function accepts a SOAP request wrapped in a JSON request and invokes
 * LambdaRequestProcessor
 */
public class SoapApiRequestHandler implements RequestHandler<SOAPMessageWrapper, SOAPMessageWrapper> {

	@Override
	public SOAPMessageWrapper handleRequest(SOAPMessageWrapper input, Context context) {

		SOAPMessageWrapper soapWrapper;

		LambdaLogger logger = context.getLogger();

		try (ByteArrayInputStream in = new ByteArrayInputStream(input.getBody().getBytes(UTF_8))) {

			SOAPMessage request = MessageFactory.newInstance().createMessage(new MimeHeaders(), in);

			// Extracts the location from the SOAP Request
			String location = Utility.getLocation(request);

			// Converts the location data to input json
			String jsonInput = Utility.getJsonForInput(location);

			logger.log("\n1 jsonInput:: " + jsonInput + "\n");

			// retrieve calling lambda function name from ENV
			String functionName = System.getenv("FUNCTION_NAME");

			// invokes the GetWeather lambda function
			String jsonOutput = Utility.invokeLambdaFunction(functionName, jsonInput);
			
			// converts the json output to the soap message response
			soapWrapper = Utility.jsonToSoapWrapper(jsonOutput);

		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
		return soapWrapper;
	}
}
