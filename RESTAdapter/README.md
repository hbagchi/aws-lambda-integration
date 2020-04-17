# REST Adaptor

This project contains source code and supporting files for a AWS lambda function => *REST Adaptor*. This Serverless function represents a RESTful endpoint for the [Get Weather Service](https://github.com/hbagchi/aws-lambda-integration/tree/master/GetWeatherService) and was generated, build, packaged and deployed using [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/index.html). It includes the following files and folders.

- `rest-adaptor` - Folder for the application's Lambda function.
  - `app.js` - AWS lambda function entry point
- `events` - 
  - `event.json` - JS event to invoke the function.
- `rest-adaptor/tests` - Unit tests for the application code. Pending.
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
sam deploy --template-file deploy.yaml --capabilities CAPABILITY_NAMED_IAM --stack-name RESTAdapterStack
```

## API Gateway Configuration

1. Method Request - 
2. Integration Request - 
   1. Select *Use Lambda Proxy integration*
3. Integration Response - 

4. Method Response - 


*Note:  Go with generated defaults for rest of the attributes in the 4 sections*

## Unit Tests

Tests are defined in `rest-adaptor/tests` directory . Use NPM to install [Mocha](https://mochajs.org/) and [Chai](https://www.chaijs.com/) to run unit tests.

TBD

## Cleanup

To delete the sample application that you created, use the AWS CLI, you can run the following:

```powershell
aws cloudformation delete-stack --stack-name RESTAdapterStack
```

## Useful Tips

1. Please ensure your host is configured with AWS *Access Key ID* and AWS *Secret Access Key* before you run SAM CLI commands. You have to also set the default region.

   ```powershell
   aws configure
   ```

   
