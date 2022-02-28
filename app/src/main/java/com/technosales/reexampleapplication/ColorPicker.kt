package com.technosales.reexampleapplication

object ColorPicker{
    val colors =
        arrayOf("#eb4034","#eb4034","#eb4034","#eb4034","#0e807a","#214a48")
    var  colorIndex = 1
    fun getColor() : String{
        return colors[ colorIndex++ % colors.size]
    }
}