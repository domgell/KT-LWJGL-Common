package opengl

import org.lwjgl.opengl.GL15

fun createTexture(target: Int, data: Long, width: Int, height: Int, format: Int, type: Int) =
    GL15.glTexImage2D(target, 0, format, width, height, 0, format, type, data)

fun createTexture(target: Int, data: IntArray, width: Int, height: Int, format: Int, type: Int) =
    GL15.glTexImage2D(target, 0, format, width, height, 0, format, type, data)
