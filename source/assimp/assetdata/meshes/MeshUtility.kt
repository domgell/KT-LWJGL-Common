package assetdata.meshes

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.assimp.*

fun AIMesh.getPositions(): Array<Vector3f> {
    return ArrayList(mVertices().map { Vector3f(it.x(), it.y(), it.z()) }).toTypedArray()
}

private fun AIMesh.getTexCoords(): Array<Vector2f> {
    return if (mTextureCoords(0) != null)
        ArrayList(mTextureCoords(0)!!.map { Vector2f(it.x(), it.y()) }).toTypedArray()
    else
        Array(mNumVertices()) { Vector2f() }
}

private fun AIMesh.getNormals(): Array<Vector3f> {
    return if (mNormals() != null)
        ArrayList(mNormals()!!.map { Vector3f(it.x(), it.y(), it.z()) }).toTypedArray()
    else
        Array(mNumVertices()) { Vector3f() }
}

private fun AIMesh.getColors(): Array<Vector4f> {
    return if (mColors(0) != null)
        ArrayList(mColors(0)!!.map { Vector4f(it.r(), it.g(), it.b(), it.a()) }).toTypedArray()
    else
        Array(mNumVertices()) { Vector4f(1f, 1f, 1f, 1f) }
}

// TODO Refactor
internal fun AIMesh.getIndices(): IntArray {
    val buffer = this.mFaces()
    val indices = ArrayList<Int>()
    buffer.forEach { face ->
        val numIndices = face.mNumIndices()
        val indexBuffer = face.mIndices()

        for (i in 0 until numIndices) {
            indices.add(indexBuffer[i])
        }
    }
    return indices.toIntArray()
}

internal fun AIMesh.createVertexData(): VertexData =
    VertexData(getPositions(), getTexCoords(), getNormals(), getColors())