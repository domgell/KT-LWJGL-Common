package application

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil


class Window(width: Int, height: Int) {
    val glfwHandle = createWindow(width, height)
    
    var shouldClose: Boolean
        get() = GLFW.glfwWindowShouldClose(glfwHandle)
        set(value) = GLFW.glfwSetWindowShouldClose(glfwHandle, value)
    
    fun pollEvents() = GLFW.glfwPollEvents()

    fun close() = destroyWindow(glfwHandle)
}

// Initializes a new GLFW window and returns the window handle
internal fun createWindow(width: Int,  height: Int): Long {
    // Set up error callback
    GLFWErrorCallback.createPrint(System.err).set()
    
    // Initialize GLFW
    if (!GLFW.glfwInit())
        throw IllegalStateException("Unable to initialize GLFW")

    // Set up GLFW window
    GLFW.glfwDefaultWindowHints()
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)     // Focus on window creation
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)    // Enable resizing
    GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8)             // Enable MSAA 

    // Create the window
    val windowHandle = GLFW.glfwCreateWindow(width, height, "LWJGL Engine", MemoryUtil.NULL, MemoryUtil.NULL)
    if (windowHandle == MemoryUtil.NULL)
        throw RuntimeException("Failed to create the GLFW window")

    // TEMP: What does this do ??? (Removing it changes nothing)
    // Get the thread stack and push a new frame
    MemoryStack.stackPush().use { stack ->
        val pWidth = stack.mallocInt(1) // int*
        val pHeight = stack.mallocInt(1) // int*

        // Get the window size passed to glfwCreateWindow
        GLFW.glfwGetWindowSize(windowHandle, pWidth, pHeight)

        // Get the resolution of the primary monitor
        val resolution = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

        // Center the window
        GLFW.glfwSetWindowPos(
            windowHandle,
            (resolution!!.width() - pWidth[0]) / 2,
            (resolution.height() - pHeight[0]) / 2
        )
    }

    GLFW.glfwMakeContextCurrent(windowHandle) // Make the OpenGL context current
    GLFW.glfwSwapInterval(1) // Enable v-sync
    GLFW.glfwShowWindow(windowHandle)

    return windowHandle
}

internal fun destroyWindow(windowHandle: Long) {
    Callbacks.glfwFreeCallbacks(windowHandle)
    GLFW.glfwDestroyWindow(windowHandle)
    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null)?.free()
}