"use strict"

var AWS = require('aws-sdk')
AWS.config.region = 'us-east-1'

var lambda = new AWS.Lambda()

let response

/**
 * @param {Object} event - API Gateway Lambda Proxy Input Format
 * 
 */
exports.lambdaHandler = async (event) => {

    let cityName = ""

    try {

        if (event.body) {

            let body = event.body

            if (typeof body != 'object') {
                body = JSON.parse(body)
            }
        
            if (body.city) {
                cityName = body.city
            }
        }

        console.log("city Input: " + cityName)

        var params = {
            FunctionName: 'GetWeatherService', // the lambda GetWeatherService Lambda function
            InvocationType: "RequestResponse", 
            LogType: "Tail", 
            Payload: `{ "queryStringParameters": { "city": "${cityName}" } }`
        }

        await lambda.invoke(params, function (err, data) {

            if (err) {
                throw err
            } else {
                var weatherResponse = JSON.parse(data.Payload)

                console.log('response:: ' + JSON.stringify(weatherResponse))

                response = {
                    'statusCode': 200,
                    'headers': { 'Content-Type': 'application/json' },
                    'body': JSON.stringify({
                        'message': 'SUCCESS',
                        'city': weatherResponse.body.city,
                        'currentTemperature': weatherResponse.body.currentTemperature
                    })
                }        
            }
        }).promise()

        return response 

    } catch (err) {
        throw err
    }
}
