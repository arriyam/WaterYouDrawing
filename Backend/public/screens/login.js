
function login() {
    input.style('width', '15%')
    input.style('height', '5%')
    input.position(w/2, l/2)
    input.style('line-height', '4')
    input.style('fontSize', '18px')
    input.style('placeholder', 'Username')

    button.style('width', '15.4%')
    button.style('height', '5%')
    button.position(w/2, l/2+(l*0.1))
    button.style('color', '#fff !important')
    button.style('background-color', '#60a3bc')
    button.style('text-transform', 'uppercase')
    button.style('transition', 'all 0.4s ease 0s;')
    button.style('border-radius', '50px')
    button.style('border', 'none')

    input.show()
    button.show()

    //background(255, 255, 255);
    textSize(40)
    textAlign(CENTER)
    text("Water!", w/2, l*0.2);


    button.mousePressed(()=> { //When username is submitted, hide form and button
      if (input.value() != "") { //WILL NEED TO CHECK IF USERNAME HAS ALREADY BEEN USED ON SERVER
          user.loggedIn = true;
          user.name = input.value()
          user.state = 1
          socket.emit("join", user) 
          button.hide()
          input.hide()
        } else {
          console.log("Must put in username")
        }

     });

    
}