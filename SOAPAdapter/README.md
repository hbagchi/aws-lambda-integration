# SOAP Adapter

This project contains source code and supporting files for a AWS lambda function => *SOAP Adaptor*. This Serverless function represents a SOAP endpoint for the [Get Weather Service](https://github.com/hbagchi/aws-lambda-integration/tree/master/GetWeatherService) and was generated, build, packaged and deployed using [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/index.html). It includes the following files and folders.

- `soap-adaptor` - Folder for the application's Lambda function.
  - `com.home.lambda.SoapApiRequestHandler` - Java class for AWS lambda function entry point
  - `com.home.lambda.pojo` - Java package that contains classes to map to json
  - `com.home.lambda.util` - java package that contains AWS lambda utility methods
- `events` - 
  - `event.json` - JS event to invoke the function.
- `soap-adaptor/src/tests` - Unit tests for the application code. Pending.
- `template.yaml` - A template that defines the application's AWS resources.

The application uses AWS Lambda function only. These resources are defined in the `template.yaml` file.

## Application Development

To generate, build, package and and deploy the application, run the following in windows command prompt:

Initialize a default SAM project

```shell
sam init
```

Invoke function locally with `event.json` containing the event input

```powershell
sam local invoke "GetWeatherRESTAdapterFunction" --event events/event.json
```

Build and package the application. 

```powershell
sam build && sam package --s3-bucket [lambda-bucket] --s3-prefix REST --output-template-file deploy.yaml
```

Note: Please ensure `lambda-bucket` is unique across AWS  

Deploy the application to AWS. Please note you have to provide capabilities and stack name as below.

```powershell
sam deploy --template-file deploy.yaml --capabilities CAPABILITY_NAMED_IAM --stack-name SOAPAdapterStack
```

## API Gateway Configuration

1. Method Request - 

2. Integration Request - 

   1. Type => Lambda function
   2. Mapping Templates
      1. Request body passthrough => When there are no templates defined (recommended)
      2. Content-Type: 
         
         1. application/xml => 
         
            ```velocity
            { "body" : $input.json('$') }
            ```
         
            

3. Integration Response - 

   1. Mapping Templates

      1. Content-Type: application/xml => 

         ```velocity
         #set($inputRoot = $input.path('$'))
         <?xml version="1.0" encoding="UTF-8"?>
         $inputRoot.body
         ```
         
         Method Response - 

   1. HTTP Status 200
      1. Response Body for 200
         1. Content-Type: application/soap+xml
         2. Models: Empty


*Note:  Go with generated defaults for rest of the attributes in the 4 sections*

## Unit Tests

Tests are defined in `soap-adaptor/tests` directory . Use NPM to install [Mocha](https://mochajs.org/) and [Chai](https://www.chaijs.com/) to run unit tests.

TBD

## Cleanup

To delete the sample application that you created, use the AWS CLI, you can run the following:

```powershell
aws cloudformation delete-stack --stack-name SOAPAdapterStack
```

## Useful Tips

1. Please ensure your host is configured with AWS *Access Key ID* and AWS *Secret Access Key* before you run SAM CLI commands. You have to also set the default region.

   ```powershell
   aws configure
   ```


## TODO Items

1. In AWS Lambda **Java 11** runtime, `aws-lambda-java-core` or `aws-java-sdk-lambda` are not available by default. Hence, these AWS lambda jars have to be included in the build which adds about `2mb` to the deployable file size. However, the same is not true for **Node.js 12.x** runtime. For NodeJS, the `aws-sdk` is available in the **Node.js 12.x** runtime. So no need to add `aws-sdk`. 

## References

1. [Legacy SOAP API integration with Java, AWS Lambda and AWS API Gateway](https://blog.codecentric.de/en/2016/12/serverless-soap-legacy-api-integration-java-aws-lambda-aws-api-gateway/)
2. [How to configure Amazon API Gateway as a SOAP webservice passthrough in minutes](https://www.rubix.nl/blogs/how-configure-amazon-api-gateway-soap-webservice-passthrough-minutes/)
3. [Support SOAP/XML request / response passthrough in API Gateway](https://forums.aws.amazon.com/thread.jspa?threadID=236702)