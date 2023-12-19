package input

import application.App

abstract class InputBinding(private val onDown: () -> Unit, private val onUp: () -> Unit) {
    abstract fun isDown(): Boolean
    private var wasDown = false
    
    init {
       App.onUpdate {
           if (!wasDown && isDown())
               onDown()
           else if (wasDown && !isDown())
               onUp()

           wasDown = isDown()
       } 
    }
}

fun Keys.bind(onDown: () -> Unit, onUp: () -> Unit = {}) {
    object : InputBinding(onDown, onUp) {
        override fun isDown(): Boolean = this@bind.isDown
    }
}

fun MouseButtons.bind(onDown: () -> Unit, onUp: () -> Unit = {}) {
    object : InputBinding(onDown, onUp) {
        override fun isDown(): Boolean = this@bind.isDown
    }
}


