package com.arriyam.newsocketiotest2.sketch

object CanvasSize {
    var height:Float=0f
    var width:Float=0f

    fun setCanvasHeight(getHeight:Float){
        height=getHeight
    }
    fun setCanvasWidth(getWidth:Float){
        width=getWidth
    }

    fun getCanvasHeight():Float{
        return height
    }

    fun getCanvasWidth():Float{
        return width
    }
}