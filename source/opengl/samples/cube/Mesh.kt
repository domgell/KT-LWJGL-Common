package opengl.samples.cube

import opengl.attributePointer
import org.joml.Matrix4f
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
import java.awt.Color

class Mesh(val indices: IntArray, positions: FloatArray) {
    val transform = Matrix4f().identity()!!
    var color: Color = Color.darkGray
    var drawMode: Int = GL30.GL_TRIANGLES

    private val vao = GL30.glGenVertexArrays()

    init {
        GL30.glBindVertexArray(vao)
        val ibo = GL15.glGenBuffers()

        // Bind index buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW)

        attributePointer(0, positions, 3)
    }

    fun use() {
        GL30.glBindVertexArray(vao)
        GL30.glEnableVertexAttribArray(0)
    }
}