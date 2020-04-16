'use strict';

const expect = require('chai').expect;
const sinon = require('sinon');
const axios = require('axios');

const apikey = process.env.API_KEY
const unit = "metric"

const deps = {
    // Use sinon.stub(..) to prevent any calls to OpenWeatherMAP API and enable faking of methods
    axios: sinon.stub(axios),
    apikey: apikey,
    unit: unit
};

const handler = require('../../handler')(deps);

// Keep test output free of error messages printed by our lambda function
sinon.stub(console, 'error');

describe('Get Weather Service Handler', function () {

    afterEach(sinon.reset);

    it('calls OpenWeatherMap API and returns result', async () => {

        const event = {
            httpMethod: 'GET',
            queryStringParameters: {
                city: "milan"
            }
        };
        // Fake axios client behavior
        deps.axios.get.returns({
            data: {
                main: {
                    temp: 16.0
                }
            }
        });
        const result = await handler(event);

        sinon.assert.calledWith(deps.axios.get);

        expect(result).to.be.an('object');
        expect(result.statusCode).to.equal(200);
        expect(result.body).to.be.an('object');

        expect(result.body.currentTemperature).to.equal(16.0);
    });
});
