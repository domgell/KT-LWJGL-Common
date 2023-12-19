package assetdata.meshes

import assetdata.animations.BoneNode
import assetdata.animations.MeshAnimation
import assetdata.animations.createAnimData
import org.joml.Vector2f
import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector4f
import org.joml.Vector4fc
import org.joml.Vector4i
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIScene

data class VertexData(
    val vPositions: Array<Vector3f>,
    val vTexCoords: Array<Vector2f>,
    val vNormals: Array<Vector3f>,
    val vColors: Array<Vector4f>
)

data class AnimationData(
    val boneNodes: Array<BoneNode>,
    val animations: Array<MeshAnimation>,
    val vWeights: Array<Vector4f>,
    val vBoneInfluences: Array<Vector4i>
)

data class MeshData(val indices: IntArray, val vertexData: VertexData, val animationData: AnimationData?) {
    companion object {
        fun create(aiMesh: AIMesh, aiScene: AIScene): MeshData {
            return MeshData(aiMesh.getIndices(), aiMesh.createVertexData(), aiMesh.createAnimData(aiScene))
        }
    }
}