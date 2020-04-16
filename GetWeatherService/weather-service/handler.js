'use strict'

let response

const AWS = require('aws-sdk')
const kms = new AWS.KMS();

/**
 *
 * @param {Object} event - Input data
 * @returns {Object} object - output data
 * 
 */
module.exports = deps => async (event) => {

    let cityName = ''
    
    try {

        if (event.queryStringParameters && event.queryStringParameters.city) {
            cityName = event.queryStringParameters.city;
        }

        const params = {
            CiphertextBlob: Buffer.from(deps.encApiKey, 'base64')
        }
    
        const { Plaintext } = await kms.decrypt(params).promise()

        const apiKey = Plaintext

        // OpenWeatherMapAPI
        const openWeatherMapAPIURL = `https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=${apiKey}&units=${deps.unit}`

        const currentWeather = await deps.axios.get(openWeatherMapAPIURL)

        response = {
            statusCode: 200,
            body: {
                message: "SUCCESS",
                city: cityName,
                currentTemperature: currentWeather.data.main.temp
            }
        }
    } catch (err) {
        response = {
            statusCode: 401,
            body: {
                message: err.message
            }
        }
    }
    return response
}
