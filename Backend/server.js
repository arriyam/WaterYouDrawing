const express = require('express'); //requires express module
const socket = require('socket.io'); //requires socket.io module
var Timer = require('time-counter')
const fs = require('fs');
const { clear } = require('console');
const app = express();
var PORT = process.env.PORT || 3000;
const server = app.listen(PORT); //tells to host server on localhost:3000

//Playing variables:
app.use(express.static('public')); //show static files in 'public' directory
console.log('Server is running');
const io = socket(server);

//Game Variables:
var gameActive = false;
var runOnce = false;
var runOnce2 = false;
var runOnce3 = false;

let users = [];
let startingCounter;

let drawTimer;

var countDownTimerStarting = new Timer({
    direction: "down",
    startValue: 1,
});


countDownTimerStarting.on('change', (y) => {
    startingCounter = y;
});

var runState = true;

var mouseAndroid = 0;
//P5 Socket.io Connection------------------
io.on('connection', (socket) => {


    //checks that there are at least 3 users connected before starting the game 
    socket.on('gameState', () => {
        if (users.length >= 3) {
            if (runOnce == false) {
                runOnce = true;
                countDownTimerStarting.start();
            }

            if (startingCounter === "0:00") {
                if (runOnce2 === false) {
                    runOnce2 = true;
                    runOnce3 = false;
                    runState = true;
                    io.emit('gameState', -1)
                    console.log("done")
                }
            } else {
                io.emit('gameState', 2) //Game starting soon (start countdown timer)
                console.log("starting soon")

            }

        } else {
            if (runState === true) {
                io.emit('gameState', 1)
                startingCounter = null
                runOnce = false
                runOnce2 = false
                console.log("loop")
                runState = false;
            }

            // if (runOnce3 === false) {
            //     console.log("Stop timer")
            //     countDownTimerStarting.stop();
            //     startingCounter=null
            //      //Waiting screen
            //     runOnce = false
            //     runOnce2 = false
            //     runOnce3 = true;
            // }
        }
    })

    //Sends back value of starting countdown timer
    socket.on('timerVal', (a) => {
        io.emit('counterVal', startingCounter)
    })

    //Clients emits this and a user loggs in --> sends back number of connected users
    socket.on('join', (username) => {

        if (typeof username === 'string') {
            username = objectConvert(username)
        }

        const user = {
            username: username.name,
            id: socket.id,
            points: 0,
            state: username.state
        }
        users.push(user);
        io.emit('numberConnected', users.length)
    })

    //Sends back number of connected users to all connected clients
    socket.on('numberConnected', () => {
        io.emit('numberConnected', users.length)
    })


    //Drawing --> receives and send backs x,y coordinates for drawing
    socket.on('mouse', (data) => {
        if (typeof data === 'string') {
            data = objectConvert(data)
        }
        socket.broadcast.emit('mouse', data)

        if (mouseAndroid === 0) {
            socket.broadcast.emit('mouseAndroidStart', JSON.stringify(data))
            mouseAndroid = 1;
        } else if (mouseAndroid === 1) {
            socket.broadcast.emit('mouseAndroidMiddle', JSON.stringify(data))
        } else {
            socket.broadcast.emit('mouseAndroidEnd', JSON.stringify(data))
            console.log("hen")
            mouseAndroid = 0;
        }
    })
    socket.on('release', () => {
        mouseAndroid = 0
        console.log("release " + mouseAndroid)
    })

    socket.on('clear', () => {
        socket.broadcast.emit('clear')
    })


    //Removes the specific socket from the "users" array and sends back the new number of connected players
    socket.on("disconnect", () => {
        i = 0;
        console.log("DISCONNECTED: " + socket.id);
        users = users.filter(u => u.id !== socket.id)
        io.emit('numberConnected', users.length)
    });
})


function objectConvert(str) {
    str = str.substring(str.indexOf("("))
    str = str.replace("(", "");
    str = str.replace(")", "");
    var arr = str.split(", ")
    var keyval;
    var obj = {}
    for (var i = 0; i < arr.length; i++) {
        keyval = arr[i].split("=")
        if (isNaN(keyval[1]) === false) {
            keyval[1] = parseInt(keyval[1])
        }
        obj[keyval[0]] = keyval[1]
    }
    return obj
}