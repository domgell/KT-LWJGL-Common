package input

import application.App
import application.MyEvent
import org.joml.Vector2f
import org.joml.Vector2fc
import org.lwjgl.glfw.GLFW

// TODO Make this an object
class Mouse {
    val onMove = MyEvent<Vector2f>()

    private var previousPosition = Vector2f()
    private var delta = Vector2f()

    var captureCursor: Boolean = false
        set(value) {
            field = value
            if (value) {
                GLFW.glfwSetInputMode(App.window.glfwHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED)
            }
            else {
                GLFW.glfwSetInputMode(App.window.glfwHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL)
                previousPosition.set(getCursorPosition())
            }
        }
        
    init {
        App.onUpdate {
            // Calculate mouseDelta each frame
            previousPosition.sub(getCursorPosition(), delta)
            previousPosition.set(getCursorPosition())
            
            // Call onMove only when mouse moved
            if (delta.x != 0f || delta.y != 0f)
                onMove(delta)
        }
    }

    // TODO Don't create new vector2 instance every time
    private val xArr = DoubleArray(1)
    private val yArr = DoubleArray(1)
    private fun getCursorPosition(): Vector2f {
        GLFW.glfwGetCursorPos(App.window.glfwHandle, xArr, yArr)
        return Vector2f(xArr[0].toFloat(), yArr[0].toFloat())
    }
    
    private fun setCursorPosition(value: Vector2fc) {
        GLFW.glfwSetCursorPos(App.window.glfwHandle, value.x().toDouble(), value.y().toDouble())
    }
}
