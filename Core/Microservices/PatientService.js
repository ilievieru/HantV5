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
var serviceId = tools.rand(20,40);
var type = "PatientData";
var patientId1 = tools.rand(5);
var patientId2 = tools.rand2(6,10);
var patientId3 = tools.rand2(10,20);
var port = process.argv[2] === undefined ? 8087 : process.argv[2];

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

    res.end("[SmokeSensor] Raw data has been successfully sent.");
})

app.get('/GenerateStart', function (req, res) {
    console.log("[" + type + "] Sending raw data at random intervals...");
    res.end("[" + type + "] Sending raw data at random intervals...");

    started = true;
    continuousPublishing = setTimeout(publish, tools.getRandomInterval(3000, 7000), generateRawData());
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
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateRawData(){
    return {
        "ID": getRandomInt(10,50),
        "Type": type,
        "Patients":[
            {
                "PatientId": getRandomInt(0,15),
                "Cam": getRandomInt(1,19),
                "Age": getRandomInt(9,75),
                "IsMoving": tools.rand(3) == 2 ? true : false,
                "Pulse": tools.rand2(67,87),
                "BodyTemperature": tools.rand2(360, 370)/10,
                "Height": 1.64
            },
            {
                "PatientId": getRandomInt(15,30),
                "Cam": getRandomInt(1,19),
                "Age": getRandomInt(9,75),
                "IsMoving": tools.rand(3) == 2 ? true : false,
                "Pulse": tools.rand2(67,87),
                "BodyTemperature": tools.rand2(360, 370)/10,
                "Height": 1.751
            },
            {
                "PatientId": getRandomInt(30,45),
                "Cam": getRandomInt(1,19),
                "Age": getRandomInt(9,75),
                "IsMoving": tools.rand(3) == 2 ? true : false,
                "Pulse": tools.rand2(67,87),
                "BodyTemperature": tools.rand2(360, 370)/10,
                "Height": 1.32
            }
        ]
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
                'Content-Type' : 'application/json',
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
        continuousPublishing = setTimeout(publish, tools.getRandomInterval(3000, 7000), generateRawData());
    }
}
