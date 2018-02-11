var fs = require("fs");
var http = require('http');
var https = require('https');
var express = require('express');
var querystring = require('querystring');
var app = express();
var tools = require("./Tools.js");
app.use(express.static('StaticFiles'));
var server;

var subscribers = [];
var publications = {
    data: [],
    index: -1
};
var continuousPublishing;
var started = false;
var serviceId = 3;
var type = "ThermostatSensor";
var currentTempValue;
var port = process.argv[2] === undefined ? 8089 : process.argv[2];

app.get("/", function (req, res) {
    var response = fs.readFile("Microservice.html", (err, data) => {
        if (err) {
            res.end(404, "There was an error while processing your request!");
            console.log(err);
            return;
        }

        res.end(data.toString());
    })
    console.log("[" + type + "] Received a request to the homepage!");
})

app.get("/Connect/:addr/:port", function (req, res) {
    if (subscribers.contains({ 'address': req.params.addr, 'port': req.params.port }) >= 0) {
        res.end("[" + type + "] The subscriber already exists.");
        return;
    }

    subscribers.push({ 'address': req.params.addr, 'port': req.params.port });

    console.log("[" + type + "] Received a subscriber from: " + req.params.addr + ", port: " + req.params.port);
    res.end("[" + type + "] A new subscriber was registered.");
})

app.get('/Disconnect/:addr/:port', function (req, res) {
    console.log("[" + type + "] Received an unsubscription from: " + req.params.addr + ", port: " + req.params.port);

    var index = subscribers.contains({ 'address': req.params.addr, 'port': req.params.port })
    if (index >= 0) {
        subscribers.splice(index, 1);
        console.log("[" + type + "] Subscription was found and removed: " + req.connection.address().address + ", port: " + req.connection.address().port)
        res.end("[" + type + "] A subscription has been removed.");
        return;
    }

    res.end("[" + type + "] There is no subscription to remove.");
})

app.get('/Generate', function (req, res) {
    var postData = generateRawData();

    publish(postData);

    res.end("[" + type + "] Raw data has been successfully sent.");
})

app.get('/GenerateStart', function (req, res) {
    console.log("[" + type + "] Sending raw data at random intervals...");
    res.end("[" + type + "] Sending raw data at random intervals...");

    started = true;
    continuousPublishing = setTimeout(publish, 10, generateRawData());
})

app.get('/GenerateStop', function (req, res) {
    console.log("[" + type + "] Stopped sending raw data at random intervals...");
    res.end("[" + type + "] Stopped sending raw data at random intervals.");

    clearTimeout(continuousPublishing);
    started = false;
})

server = app.listen(port, "127.0.0.1", function () {

    var host = server.address().address
    var port = server.address().port

    console.log("[" + type + " Initializing] The " + type + " is listening for commands at http://%s:%s", host, port)
})

//////////////////////////////////////////////////
//  Functions
//////////////////////////////////////////////////

subscribers.contains = tools.SubscriberContains;

function generateRawData(){
    if(currentTempValue === undefined || currentTempValue > 130)
        currentTempValue = tools.rand2(15, 25);
    else
        currentTempValue = currentTempValue * (tools.rand2(80, 130) / 100);

    return {
        "ID": serviceId,
        "Type": type,
        "Temperature": tools.trunc(currentTempValue, 2)
    };
}

function publish(rawData) {

    var postData = JSON.stringify(rawData);
    var count = 0;
    console.log(rawData);

    for (var i = 0; i < subscribers.length; i++) 
        if(subscribers[i].sendErrorCount !== undefined && subscribers[i].sendErrorCount > 4){
            console.log("[" + type + "] Removed subscriber " + i + " (port " + subscribers[i].port + ") because it doesn't respond to the publications.")
            subscribers.splice(i--, 1);
        }

    for (var i = 0; i < subscribers.length; i++) {
        console.log("[" + type + "] Sending publication to: " + subscribers[i].address + " port: " + subscribers[i].port);

        var subscriberReq = https.request({
            'hostname': subscribers[i].address,
            'port': subscribers[i].port,
            'path': "/NotificationEndpoint",
            'method': 'POST',
            'cert': fs.readFileSync('SmokeSensorCertificate.pem'),
            'rejectUnauthorized': false,
            'headers': {
                'accept': 'text/plain',
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(postData)
            }
        }, function (resp) {
            resp.on("end", function (data) {
                if (++count == subscribers.length)
                    console.log("[" + type + "] The publication has been delivered to " + count + " subscribers.");
            });
        });

        subscriberReq.on("error", function(err){
            console.log("![" + type + "] There was an error while publishing to subscriber " + this.i + " Message:" + err.message);
            if(subscribers[this.i].sendErrorCount === undefined)
                subscribers[this.i].sendErrorCount = 1;
            else
                subscribers[this.i].sendErrorCount++;
        }.bind( {i: i} ))

        subscriberReq.end(postData);
    }

    if(started){
        clearTimeout(continuousPublishing);
        continuousPublishing = setTimeout(publish, 10, generateRawData());
    }
}