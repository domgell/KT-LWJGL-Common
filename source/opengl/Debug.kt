package opengl

import org.lwjgl.opengl.GL15

fun logErrors() {
    val error = GL15.glGetError()
    if (error != GL15.GL_NO_ERROR)
        println(error)
}