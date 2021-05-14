function waiting() {
    runOnceDrawing = false
    background(0, 100, 100);
    textSize(40)
    textAlign(CENTER)
    text(("Hello, " + user.name), w/2, l*0.2);
    textSize(20)
    text(("You are placed in the waiting room. Please wait for other players."), w/2, l*0.3);
    
    socket.emit('numberConnected', 0)
    socket.emit('gameState', 0)
    

    text(("Players: " + connectedPlayers), w/2, l*0.4);
    
    if (user.state === 1) {
        text(("Game starting soon"), w/2, l*0.6)
    } else if (user.state === 2) {
        socket.emit('timerVal', 0)
        text(("Game starting in " + countDownTimerStarting + " seconds"), w/2, l*0.6)
    }
    
}