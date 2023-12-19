package opengl

import math.VMath.toFloatArray
import math.VMath.toIntArray
import org.joml.*
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
import rendering.createBuffer

fun attributePointer(index: Int, data: FloatArray, dataSize: Int, normalized: Boolean = false, stride: Int = 0) =
    GL30.glVertexAttribPointer(index, dataSize, GL15.GL_FLOAT, normalized, stride, data.createBuffer())

fun attributePointer(index: Int, data: IntArray, dataSize: Int, stride: Int = 0) =
    GL30.glVertexAttribIPointer(index, dataSize, GL30.GL_INT, stride, data.createBuffer())

fun attributePointer(index: Int, data: Array<Matrix4fc>, normalized: Boolean = false, stride: Int = 0): Unit =
    throw NotImplementedError()

fun attributePointer(index: Int, data: Array<Vector4fc>, normalized: Boolean = false, stride: Int = 0) =
    attributePointer(index, data.toFloatArray(), 4, normalized, stride)

fun attributePointer(index: Int, data: Array<Vector3fc>, normalized: Boolean = false, stride: Int = 0) =
    attributePointer(index, data.toFloatArray(), 3, normalized, stride)

fun attributePointer(index: Int, data: Array<Vector2fc>, normalized: Boolean = false, stride: Int = 0) =
    attributePointer(index, data.toFloatArray(), 2, normalized, stride)

fun attributePointer(index: Int, data: Array<Vector4ic>, stride: Int = 0) =
    attributePointer(index, data.toIntArray(), 4, stride)

fun attributePointer(index: Int, data: Array<Vector3ic>, stride: Int = 0) =
    attributePointer(index, data.toIntArray(), 3, stride)

fun attributePointer(index: Int, data: Array<Vector2ic>, stride: Int = 0) =
    attributePointer(index, data.toIntArray(), 2, stride)
