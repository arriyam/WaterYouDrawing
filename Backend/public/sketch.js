const paths = [];
let currentPath = [];
var socket;
var strokeweight = 10
var runOnceDrawing = false;
var w;
var l;
var connectedPlayers = 1;
var countDownTimerStarting;
//Player variables:


function setup() {

    w = windowWidth / 1.2;
    l = windowHeight / 1.2;
    createCanvas(w, l);
    background(50);

    //DOM--------------------
    input = createInput().attribute('placeholder', 'Username');
    button = createButton('LOGIN');

    drawingInput = createInput().attribute('placeholder', 'Guess');
    drawingButton = createButton('clear');

    input.hide()
    button.hide()

    drawingInput.hide()
    drawingButton.hide()

    //-----------------------

    //Web-------------------
    socket = io.connect("http://localhost:3000/")


    socket.on('mouse', otherDrawing)

    socket.on('counterVal', (c) => {
        countDownTimerStarting = c

    })
    socket.on('numberConnected', (number) => {
        connectedPlayers = number;
    })

    socket.on('gameState', (state) => {
        if (state === 1) {
            user.state = 1;
        } else if (state === 2) {
            user.state = 2;
        } else if (state === -1) {
            user.state = -1
        }
    })

    socket.on('clear', () => {
        background(255);
    })


    // socket.on('startGame', (a) => {
    //     user.state = -1
    // })

    user = new User("", false, 0);
}

function draw() {
    //console.log("User.state = " + user.state)
    if (user.loggedIn == false) {
        login();
    } else {
        if (user.state == 1 || user.state == 2) {
            waiting()
        } else if (user.state == -1) {
            if (runOnceDrawing === false) {
                background(255)
                runOnceDrawing = true;
                console.log("clearing")
            }
            drawing();
        }

        // if (runOnce===false) { //this will run once after user gets logged in
        //     runOnce = true;
        //     // background(200, 200, 200)
        //     text(user.name, 200, 200);
        //     console.log(user)
        // }





    }

}

function mouseReleased() {
    socket.emit("release")
}