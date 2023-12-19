package opengl

import math.MMath.toFloatArray
import org.joml.Matrix4fc
import org.joml.Vector2fc
import org.joml.Vector3fc
import org.joml.Vector4fc
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

fun setUniform(value: Matrix4fc, index: Int) = GL20.glUniformMatrix4fv(index, false, value.toFloatArray())

fun setUniform(value: Vector4fc, index: Int) = GL20.glUniform4f(index, value.x(), value.y(), value.z(), value.w())

fun setUniform(value: Vector3fc, index: Int) = GL20.glUniform3f(index, value.x(), value.y(), value.z())

fun setUniform(value: Vector2fc, index: Int) = GL20.glUniform2f(index, value.x(), value.y())

fun setUniform(value: Float, index: Int) = GL20.glUniform1f(index, value)

fun setUniform(value: Int, index: Int) = GL20.glUniform1i(index, value)

fun getUniform(name: String, programHandle: Int): Int {
    /*
    val i = GL30.glGetUniformLocation(programHandle, name)
    if (i == -1)
        throw IllegalArgumentException("The uniform '$name' does not exist on the provided shaderProgram")
    return i*/
    return GL30.glGetUniformLocation(programHandle, name)
}