package opengl.samples.cube

import math.VMath.toFloatArray
import org.joml.Vector3f
import org.joml.Vector3fc
import org.lwjgl.opengl.GL15
import java.awt.Color

// TODO REFACTOR
fun createGridMesh(color: Color): Mesh {
    fun addLine(vertices: ArrayList<Vector3fc>, indices: ArrayList<Int>, start: Vector3fc, end: Vector3fc) {
        val startIndex = vertices.size
        vertices.add(start)
        vertices.add(end)
        indices.add(startIndex)
        indices.add(startIndex + 1)
    }

    val gridSize = 40
    val spacing = 0.5f
    val positions = ArrayList<Vector3fc>()
    val indices = ArrayList<Int>()

    for (i in -gridSize / 2..gridSize / 2) {
        val x = i * spacing
        val zMin = -gridSize / 2f * spacing
        val zMax = gridSize / 2f * spacing

        // Vertical lines (along X-axis)
        addLine(positions, indices, Vector3f(x, 0.0f, zMin), Vector3f(x, 0.0f, zMax))

        // Horizontal lines (along Z-axis)
        addLine(positions, indices, Vector3f(zMin, 0.0f, x), Vector3f(zMax, 0.0f, x))
    }

    // Create the grid mesh
    val m = Mesh(indices.toIntArray(), positions.toTypedArray().toFloatArray())
    m.color = color
    m.drawMode = GL15.GL_LINES
    m.transform.translate(Vector3f(0f, -1f, 0f))

    return m
}