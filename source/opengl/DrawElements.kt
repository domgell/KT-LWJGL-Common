package opengl

import org.lwjgl.opengl.GL15

fun drawElements(numIndices: Int, drawMode: Int) = GL15.glDrawElements(drawMode, numIndices, GL15.GL_UNSIGNED_INT, 0)
