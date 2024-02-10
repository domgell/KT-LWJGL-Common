package opengl.samples.animation

import application.Disposable
import opengl.attributePointer
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL45.*

class MeshDataHandler(indices: IntArray) : Disposable {
    private val vao = glGenVertexArrays()
    private var attributeIndices = ArrayList<Int>()
    val numIndices = indices.size
    
    init {
        glBindVertexArray(vao)
        
        val ibo = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)
    }
    
    fun use() {
        glBindVertexArray(vao)
        attributeIndices.forEach { glEnableVertexAttribArray(it) }
    }
    
    fun addAttribute(index: Int, data: FloatArray, size: Int) {
        attributeIndices.add(index)
        attributePointer(index, data, size)
    }

    fun addAttribute(index: Int, data: IntArray, size: Int) {
        attributeIndices.add(index)
        attributePointer(index, data, size)
    }
    
    // Override the data at the given index
    fun setAttribute(index: Int, data: FloatArray, size: Int) {
        attributePointer(index, data, size)
    }

    // Override the data at the given index
    fun setAttribute(index: Int, data: IntArray, size: Int) {
        attributePointer(index, data, size)
    }
    
    override fun dispose() {
        GL30.glDeleteVertexArrays(vao)
    }
}