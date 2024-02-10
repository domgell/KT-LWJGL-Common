package opengl

import math.MMath.toFloatArray
import org.joml.Matrix4fc
import org.joml.Vector2fc
import org.joml.Vector3fc
import org.joml.Vector4fc
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL45.*
import rendering.toNormalizedColor
import java.awt.Color

private fun validateSetUniform(index: Int) {
    if (index <= -1)
        throw IllegalStateException("Attempted to set invalid uniform")
}

private fun validateGetUniform(name: String, index: Int) {
    if (index == -1)
        //throw IllegalArgumentException("Invalid uniform '$name'")
        println("Unused uniform '$name'")
}



fun setUniform(value: Matrix4fc, index: Int) {
    validateSetUniform(index)
    glUniformMatrix4fv(index, false, value.toFloatArray())
}

fun setUniform(value: Vector4fc, index: Int) {
    validateSetUniform(index)
    glUniform4f(index, value.x(), value.y(), value.z(), value.w())
}

fun setUniform(value: Vector3fc, index: Int) {
    validateSetUniform(index)
    glUniform3f(index, value.x(), value.y(), value.z())
}

fun setUniform(value: Vector2fc, index: Int) {
    validateSetUniform(index)
    glUniform2f(index, value.x(), value.y())
}

fun setUniform(value: Float, index: Int) {
    validateSetUniform(index)
    glUniform1f(index, value)
}

fun setUniform(value: Int, index: Int) {
    validateSetUniform(index)
    glUniform1i(index, value)
}

fun setUniform(value: Boolean, index: Int) {
    validateSetUniform(index)
    glUniform1i(index, value.compareTo(false))
}

fun setUniform(value: Color, index: Int) = setUniform(value.toNormalizedColor(), index)

fun getUniform(name: String, programHandle: Int): Int {
    return glGetUniformLocation(programHandle, name).also { validateGetUniform(name, it) }
}

