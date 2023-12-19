package assetdata

import assetdata.meshes.MeshData
import org.joml.Matrix4f
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIScene
import assimp.utility.toMatrix4f

data class AssimpScene(val globalInverse: Matrix4f, val meshes: List<MeshData>) {
    companion object {
        fun create(aiScene: AIScene): AssimpScene {
            val meshes = List(aiScene.mNumMeshes()) { MeshData.create(AIMesh.create(aiScene.mMeshes()!![it]), aiScene) }
            
            // TODO Textures
            
            // Get global inverse transform
            val rootNode = aiScene.mRootNode() ?: throw NullPointerException("Scene has no rootNode")
            val globalInverse = rootNode.mTransformation().toMatrix4f()
            globalInverse.invert()
            
            return AssimpScene(globalInverse, meshes)
        }
    }   
}