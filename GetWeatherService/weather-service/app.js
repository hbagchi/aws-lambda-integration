const axios = require('axios')

const unit = "metric"

const encApiKey = process.env.ENC_API_KEY

module.exports.lambdaHandler = require('./handler')({
    axios: axios,
    encApiKey: encApiKey,
    unit: unit
});
