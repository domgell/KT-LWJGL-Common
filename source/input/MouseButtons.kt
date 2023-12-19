package input

import application.App
import org.lwjgl.glfw.GLFW

// Based on GLFW_MOUSE...
enum class MouseButtons(val value: Int) {
    Left(0),
    Right(1),
    Middle(2),
    Side1(3),
    Side2(4)
}

val MouseButtons.isDown: Boolean
    get() = GLFW.glfwGetMouseButton(App.window.glfwHandle, value) == GLFW.GLFW_PRESS
