# Get Weather Service

This project contains source code and supporting files for a AWS lambda function => *Get Weather Service*. This Serverless application was generated, build, packaged and deployed using [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/index.html). It includes the following files and folders.

- `weather-service` - Code for the application's Lambda function.
  - `app.js` - AWS lambda function entry point
  - `handler.js` - Node.js function to invoke [OpenWeatherMAP API](https://openweathermap.org/api)
- `events` - 
  - `event.json` - JS event to invoke the function.
- `weather-service/tests` - Unit tests for the application code. 
- `template.yaml` - A template that defines the application's AWS resources.

The application uses AWS Lambda function only. These resources are defined in the `template.yaml` file.

## Application Development

The Serverless Application Model Command Line Interface ([SAM CLI](https://docs.aws.amazon.com/serverless-application-model/index.html)) is an extension of the AWS CLI that adds functionality for building and testing Lambda applications. It uses Docker to run your functions in an Amazon Linux environment that matches Lambda. It can also emulate your application's build environment and API.

To generate, build, package and and deploy the application, run the following in windows command prompt:

Initialize a default SAM project

```shell
sam init
```

Invoke function locally with `event.json` containing the event input

```powershell
sam local invoke "GetWeatherServiceFunction" --event events/event.json
```

Build and package the application. 

```powershell
sam build && sam package --s3-bucket [lambda-bucket] --s3-prefix WeatherService --output-template-file deploy.yaml
```

Note: Please ensure `lambda-bucket` is unique across AWS  

Deploy the application to AWS. Please note you have to provide capabilities and stack name as below.

```powershell
sam deploy --template-file deploy.yaml --capabilities CAPABILITY_IAM --stack-name GetWeatherServiceStack
```

## Unit Tests

Tests are defined in `weather-service/tests` directory . Use NPM to install [Mocha](https://mochajs.org/) and [Chai](https://www.chaijs.com/) to run unit tests.

```powershell
weather-service> npm install --save-dev
weather-service> npm test
```

## Cleanup

To delete the sample application that you created, use the AWS CLI, you can run the following:

```powershell
aws cloudformation delete-stack --stack-name GetWeatherServiceStack
```

## Useful Tips

1. Please ensure your host is configured with AWS *Access Key ID* and AWS *Secret Access Key* before you run SAM CLI commands. You have to also set the default region.

   ```powershell
   aws configure
   ```

2. Instead of passing event as a `event.json` who can pass the json from the command line as a string. 

   ```powershell
   echo '{ JSON }' | sam local invoke GetWeatherServiceFunction --event - 
   ```

3. The command `sam build` & `sam package` can also be run separately without the `&&` condition. If `sam package` is run separately you have to provide the `--template-file` parameter value.