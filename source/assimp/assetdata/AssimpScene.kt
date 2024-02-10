package assimp.assetdata

import assetdata.meshes.MeshData
import assimp.assetdata.material.TextureData
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIScene
import assimp.utility.toMatrix4f
import org.joml.Matrix4fc
import org.lwjgl.assimp.AIMaterial
import org.lwjgl.assimp.AISkeleton
import org.lwjgl.assimp.AITexture

// TODO: Material for each mesh
data class AssimpScene(val globalInverse: Matrix4fc, val meshes: List<MeshData>, val textures: List<TextureData>) {
    companion object {
        fun create(aiScene: AIScene): AssimpScene {
            val meshes = List(aiScene.mNumMeshes()) { MeshData.create(AIMesh.create(aiScene.mMeshes()!![it]), aiScene) }
            
            val textures = List(aiScene.mNumTextures()) { 
                val aiTexture = AITexture.create(aiScene.mTextures()!![it])
                TextureData(aiTexture.mWidth(), aiTexture.mHeight(), aiTexture.pcDataCompressed())
            }
            
            // Get global inverse transform
            val rootNode = aiScene.mRootNode() ?: throw NullPointerException("Scene has no rootNode")
            val globalInverse = rootNode.mTransformation().toMatrix4f()
            
            return AssimpScene(globalInverse, meshes, textures)
        }
    }   
}