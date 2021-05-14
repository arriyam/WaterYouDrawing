function drawing() {

    //Styling--------------------------
    drawingInput.style('width', '15%')
    drawingInput.style('height', '5%')
    drawingInput.position(w / 2, l / 2)
    drawingInput.style('line-height', '4')
    drawingInput.style('fontSize', '18px')
    drawingInput.style('placeholder', 'Username')

    drawingButton.style('width', '15.4%')
    drawingButton.style('height', '5%')
    drawingButton.position(w / 2, (l - (l / 6)))
    drawingButton.style('color', '#fff !important')
    drawingButton.style('background-color', '#60a3bc')
    drawingButton.style('text-transform', 'uppercase')
    drawingButton.style('transition', 'all 0.4s ease 0s;')
    drawingButton.style('border-radius', '50px')
    drawingButton.style('border', 'none')
        //---------------------------------

    socket.emit('gameState', 0)
    drawingButton.show();

    drawingButton.mousePressed(() => {
        background(255);
        socket.emit('clear')
    })
    if (mouseIsPressed) {
        stroke(0);
        strokeWeight(strokeweight)
        line(mouseX, mouseY, pmouseX, pmouseY);
        mousePoint = {
            x: mouseX,
            y: mouseY,
            px: pmouseX,
            py: pmouseY,
            windowX: w,
            windowY: l
        }
        socket.emit('mouse', mousePoint)

    }




}